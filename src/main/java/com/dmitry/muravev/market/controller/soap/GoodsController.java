package com.dmitry.muravev.market.controller.soap;

import com.dmitry.muravev.market.dto.request.RateGoodsRequest;
import com.dmitry.muravev.market.dto.response.GenericMessageResponse;
import com.dmitry.muravev.market.dto.response.GoodsResponse;
import com.dmitry.muravev.market.dto.response.GoodsDetailsResponse;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;
import java.util.UUID;

@WebService(targetNamespace = "http://market.muravev.dmitry.com/", name = "GoodsService")
public interface GoodsController {

    @WebResult(name = "responseData", targetNamespace = "http://market.muravev.dmitry.com/")
    @RequestWrapper(
            localName = "GetGoodsRequest",
            targetNamespace = "http://market.muravev.dmitry.com/",
            className = "com.dmitry.muravev.market.dto.request.GetGoodsRequest")
    @WebMethod(action = "urn:getGoods")
    @ResponseWrapper(
            localName = "GetGoodsResponse",
            targetNamespace = "http://market.muravev.dmitry.com/",
            className = "com.dmitry.muravev.market.dto.response.GetGoodsResponse")
    GoodsResponse getGoods();

    @WebResult(name = "responseData", targetNamespace = "http://market.muravev.dmitry.com/")
    @RequestWrapper(
            localName = "GetGoodsDetailsRequest",
            targetNamespace = "http://market.muravev.dmitry.com/",
            className = "com.dmitry.muravev.market.dto.request.GetGoodsDetailsRequest")
    @WebMethod(action = "urn:getGoodsDetails")
    @ResponseWrapper(
            localName = "GetGoodsDetailsResponse",
            targetNamespace = "http://market.muravev.dmitry.com/",
            className = "com.dmitry.muravev.market.dto.response.GetGoodsDetailsResponse")
    GoodsDetailsResponse getGoodsDetails(@WebParam(name = "GoodsId") UUID goodsId,
                                         @WebParam(name = "ClientId") UUID clientId);

    @WebResult(targetNamespace = "")
    @RequestWrapper(
            localName = "SendGoodsRatingRequest",
            targetNamespace = "http://market.muravev.dmitry.com/",
            className = "com.dmitry.muravev.market.dto.request.SendGoodsRatingRequest")
    @WebMethod(action = "urn:getGoodsDetails")
    @ResponseWrapper(
            localName = "RateGoodsResponse",
            targetNamespace = "http://market.muravev.dmitry.com/",
            className = "com.dmitry.muravev.market.dto.response.RateGoodsResponse")
    GenericMessageResponse rateGoods(@WebParam(name = "GoodsId") UUID goodsId,
                                     @WebParam(name = "RateData") RateGoodsRequest request);
}
