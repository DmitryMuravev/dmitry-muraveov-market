package com.dmitry.muravev.market.config;

import com.dmitry.muravev.market.controller.soap.ClientController;
import com.dmitry.muravev.market.controller.soap.GoodsController;
import com.dmitry.muravev.market.controller.soap.SellController;
import com.dmitry.muravev.market.controller.soap.StatisticController;
import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.ws.Endpoint;

@Configuration
public class WebServiceConfig {

    @Autowired
    private Bus bus;

    @Bean
    public Endpoint clientEndpoint(ClientController clientController) {
        EndpointImpl endpoint = new EndpointImpl(bus, clientController);
        endpoint.publish("/ClientService");

        return endpoint;
    }

    @Bean
    public Endpoint goodsEndpoint(GoodsController goodsController) {
        EndpointImpl endpoint = new EndpointImpl(bus, goodsController);
        endpoint.publish("/GoodsService");

        return endpoint;
    }

    @Bean
    public Endpoint sellEndpoint(SellController sellController) {
        EndpointImpl endpoint = new EndpointImpl(bus, sellController);
        endpoint.publish("/SellService");

        return endpoint;
    }

    @Bean
    public Endpoint statisticEndpoint(StatisticController statisticController) {
        EndpointImpl endpoint = new EndpointImpl(bus, statisticController);
        endpoint.publish("/StatisticService");

        return endpoint;
    }
}
