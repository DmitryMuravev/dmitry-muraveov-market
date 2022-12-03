package com.dmitry.muravev.market.controller.soap;

import com.dmitry.muravev.market.dto.request.PurchaseRequest;
import com.dmitry.muravev.market.dto.request.TotalCostRequest;
import com.dmitry.muravev.market.dto.response.PurchaseResponse;
import com.dmitry.muravev.market.dto.response.TotalCostResponse;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

@WebService(targetNamespace = "http://market.muravev.dmitry.com/", name = "SellService")
public interface SellController {

    @WebResult(name = "responseData", targetNamespace = "http://market.muravev.dmitry.com/")
    @RequestWrapper(
            localName = "GetTotalCostRequest",
            targetNamespace = "http://market.muravev.dmitry.com/",
            className = "com.dmitry.muravev.market.dto.request.GetTotalCostRequest")
    @WebMethod(action = "urn:GetTotalCost")
    @ResponseWrapper(
            localName = "GetTotalCostResponse",
            targetNamespace = "http://market.muravev.dmitry.com/",
            className = "com.dmitry.muravev.market.dto.response.GetTotalCostResponse")
    TotalCostResponse getTotalCost(@WebParam(name = "RequestData") TotalCostRequest request);

    @WebResult(name = "responseData", targetNamespace = "http://market.muravev.dmitry.com/")
    @RequestWrapper(
            localName = "SendPurchaseRequest",
            targetNamespace = "http://market.muravev.dmitry.com/",
            className = "com.dmitry.muravev.market.dto.request.SendPurchaseRequest")
    @WebMethod(action = "urn:Purchase")
    @ResponseWrapper(
            localName = "SendPurchaseResponse",
            targetNamespace = "http://market.muravev.dmitry.com/",
            className = "com.dmitry.muravev.market.dto.response.SendPurchaseResponse")
    PurchaseResponse purchase (@WebParam(name = "RequestData") PurchaseRequest request);
}
