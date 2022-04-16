package com.amazonaws.lambda.service;

import com.amazonaws.lambda.entities.Client;
import com.amazonaws.lambda.entities.Response;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProviderClientBuilder;
import com.amazonaws.services.cognitoidp.model.AdminDeleteUserRequest;
import com.amazonaws.services.cognitoidp.model.SignUpRequest;
import com.amazonaws.services.cognitoidp.model.SignUpResult;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;


public class RequestProcess {
	
	AmazonDynamoDB DbClient = AmazonDynamoDBClientBuilder
			.standard()
			.withRegion(Regions.AP_SOUTH_1)
			.build();
	
	AWSCognitoIdentityProvider cognitoProvider = AWSCognitoIdentityProviderClientBuilder
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
				
				/*
				 * AdminDeleteUserRequest adminDeleteUserRequest = new AdminDeleteUserRequest()
				 * .withUsername("shreetik") .withUserPoolId("ap-south-1_xHK3D63CZ");
				 * 
				 * 
				 * cognitoProvider.adminDeleteUser(adminDeleteUserRequest);
				 */				
				
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
			

		default:
			response.setStatus("400");
			response.setMsg("action error");
			return response;
		}
		
	}
	
}
