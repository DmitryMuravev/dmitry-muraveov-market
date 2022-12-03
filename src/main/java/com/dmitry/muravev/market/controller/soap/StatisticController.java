package com.dmitry.muravev.market.controller.soap;

import com.dmitry.muravev.market.dto.request.StatisticType;
import com.dmitry.muravev.market.dto.response.StatisticResponse;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;
import java.util.UUID;

@WebService(targetNamespace = "http://market.muravev.dmitry.com/", name = "StatisticService")
public interface StatisticController {

    @WebResult(name = "responseData", targetNamespace = "http://market.muravev.dmitry.com/")
    @RequestWrapper(
            localName = "GetStatisticRequest",
            targetNamespace = "http://market.muravev.dmitry.com/",
            className = "com.dmitry.muravev.market.dto.request.GetStatisticRequest")
    @WebMethod(action = "urn:GetStatistic")
    @ResponseWrapper(
            localName = "GetStatisticResponse",
            targetNamespace = "http://market.muravev.dmitry.com/",
            className = "com.dmitry.muravev.market.dto.response.GetStatisticResponse")
    StatisticResponse getStatistic(@WebParam(name = "Id")UUID id,
                                   @WebParam(name = "StatisticType")StatisticType statisticType);
}
