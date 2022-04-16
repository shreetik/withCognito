package com.amazonaws.lambda.demo;

import com.amazonaws.lambda.entities.Client;
import com.amazonaws.lambda.entities.Request;
import com.amazonaws.lambda.entities.Response;
import com.amazonaws.lambda.service.RequestProcess;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class LambdaFunctionHandler implements RequestHandler<Request, Response> {

    @Override
    public Response handleRequest(Request input, Context context) {
        context.getLogger().log("Input: " + input);
        RequestProcess requestProcess = new RequestProcess();
        Response response = new Response();
        Client client = new Client();
        
       try {
		String action = input.getAction();
		String code = input.getCode();
		
		client.setClient_name(input.getClient_name());
		client.setEmail_id(input.getEmail_id());
		client.setCompany_name(input.getCompany_name());
		client.setPhone_no(input.getPhone_no());
		
	return requestProcess.doProcess(action, code, client);
		
		
	} catch (Exception e) {
		response.setStatus("404");
		response.setMsg("main error");
		return response;
	}
        
    }

}
