package com.dmitry.muravev.market.controller.soap;

import com.dmitry.muravev.market.dto.request.UpdateClientDiscountsRequest;
import com.dmitry.muravev.market.dto.response.GenericMessageResponse;
import com.dmitry.muravev.market.dto.response.ClientsResponse;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;
import java.util.UUID;

@WebService(targetNamespace = "http://market.muravev.dmitry.com/", name = "ClientService")
public interface ClientController {
    @WebResult(name = "responseData", targetNamespace = "http://market.muravev.dmitry.com/")
    @RequestWrapper(
            localName = "GetClientsRequest",
            targetNamespace = "http://market.muravev.dmitry.com/",
            className = "com.dmitry.muravev.market.dto.request.GetClientsRequest")
    @WebMethod(action = "urn:GetClients")
    @ResponseWrapper(
            localName = "GetClientsResponse",
            targetNamespace = "http://market.muravev.dmitry.com/",
            className = "com.dmitry.muravev.market.dto.response.GetClientsResponse")
    ClientsResponse getClients();

    @WebResult(name = "responseData", targetNamespace = "http://market.muravev.dmitry.com/")
    @RequestWrapper(
            localName = "SendUpdateClientDiscountsRequest",
            targetNamespace = "http://market.muravev.dmitry.com/",
            className = "com.dmitry.muravev.market.dto.request.SendUpdateClientDiscountsRequest")
    @WebMethod(action = "urn:UpdateClientDiscounts")
    @ResponseWrapper(
            localName = "UpdateClientDiscountsResponse",
            targetNamespace = "http://market.muravev.dmitry.com/",
            className = "com.dmitry.muravev.market.dto.response.UpdateClientDiscountsResponse")
    GenericMessageResponse updateClientDiscounts(@WebParam(name = "ClientId") UUID clientId,
                                                 @WebParam(name = "DiscountData") UpdateClientDiscountsRequest request);
}
