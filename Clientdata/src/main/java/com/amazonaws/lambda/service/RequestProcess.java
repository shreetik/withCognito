package com.amazonaws.lambda.service;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

import com.amazonaws.lambda.entities.Client;
import com.amazonaws.lambda.entities.Response;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProviderClientBuilder;
import com.amazonaws.services.cognitoidp.model.AdminDeleteUserRequest;
import com.amazonaws.services.cognitoidp.model.AdminDeleteUserResult;
import com.amazonaws.services.cognitoidp.model.SignUpRequest;
import com.amazonaws.services.cognitoidp.model.SignUpResult;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.lambda.runtime.Context;


public class RequestProcess {
	
	
	
	AmazonDynamoDB DbClient = AmazonDynamoDBClientBuilder
			.standard()
			.withRegion(Regions.AP_SOUTH_1)
			.build();
	
	AWSCognitoIdentityProvider cognitoProvider = AWSCognitoIdentityProviderClientBuilder
												.standard()
												.withRegion(Regions.AP_SOUTH_1)
												.build();
	
	AmazonDynamoDB dbclient = AmazonDynamoDBClientBuilder
			.standard()
			.withRegion(Regions.AP_SOUTH_1)
			.build();
	
	Response response = new Response();
	public Response doProcess(String action,String code, Client client) {
		
		switch (action) {
		
		case "addUser":
		{
			
			try {
				
				String name = client.getEmail_id();
				String email = client.getEmail_id();
				String password = "1234@Aws";
				String clientId = "7abo7k0altqalopialteftgdu9";
				
				SignUpRequest signUpRequest = new SignUpRequest()
												.withClientId(clientId)
												.withUsername("shreetikkumar@gmail.com")
												.withPassword(password);
				
				
				  AdminDeleteUserRequest adminDeleteUserRequest = new AdminDeleteUserRequest()
				  .withUsername("lenkaasid@gmail.com").withUserPoolId("ap-south-1_tKm1HEzWU");
				  
				  
				 AdminDeleteUserResult result = cognitoProvider.adminDeleteUser(adminDeleteUserRequest);
				 	
				 
					 DynamoDBMapper mapper = new DynamoDBMapper(dbclient);
					Client obj= mapper.load(Client.class, "lenkaasid@gmail.com");
					obj.setStatus("Inactive");
					
					mapper.save(obj);
				 
				
				SignUpResult signUpResult = cognitoProvider.signUp(signUpRequest);
				
				if(signUpResult != null) {
					System.out.println("check ur email");
					
					response.setStatus("200");
					response.setMsg("check ur mail");
					
				}else {
					System.out.println("error");
					response.setStatus("400");
					response.setMsg("error");
				}
				
				
			} catch (Exception e) {
				response.setStatus("200");
				response.setMsg(e.getMessage());
			}
			
			return response;
			
		}
			case "save":{
				
				
				try {
					DynamoDBMapper mapper = new DynamoDBMapper(dbclient);
					mapper.save(client);
					response.setStatus("200");
					response.setMsg("data saved successfully");
					return response;
				} catch (Exception e) {
					response.setStatus("400");
					response.setMsg("error");
					return response;
				}
			}

		default:
			response.setStatus("400");
			response.setMsg("action error");
			return response;
		}
		
	}
	
}
