package com.dmitry.muravev.market.service;

import com.dmitry.muravev.market.TestDataProvider;
import com.dmitry.muravev.market.entity.RatingEntity;
import com.dmitry.muravev.market.repository.RatingRepository;
import com.dmitry.muravev.market.service.impl.RatingServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Map;

public class RatingServiceTest {
    private TestDataProvider testDataProvider;
    private RatingRepository ratingRepository;

    @BeforeEach
    public void init() {
        testDataProvider = new TestDataProvider();
        ratingRepository = Mockito.mock(RatingRepository.class);
    }

    @Test
    public void whenCalculateAverageRatingThenReturnCorrectValue() {
        double expectedAverageRating = 2.25;
        double currency = 0.001;

        RatingService ratingService = new RatingServiceImpl(ratingRepository);

        List<RatingEntity> ratings = testDataProvider.generateRatingList(List.of(1, 2, 2, 4));

        Assertions.assertTrue(Math.abs(expectedAverageRating
                - ratingService.calculateAverageRating(ratings)) < currency);
    }

    @Test
    public void whenMapRatingsThenReturnCorrectValue() {

        RatingService ratingService = new RatingServiceImpl(ratingRepository);

        List<RatingEntity> ratings = testDataProvider.generateRatingList(List.of(1, 2, 2, 4, 3, 5, 5, 0));

        Map<Integer, Long> ratingMap = ratingService.mapRatings(ratings);

        Assertions.assertEquals(1, ratingMap.get(0));
        Assertions.assertEquals(1, ratingMap.get(1));
        Assertions.assertEquals(2, ratingMap.get(2));
        Assertions.assertEquals(1, ratingMap.get(3));
        Assertions.assertEquals(1, ratingMap.get(4));
        Assertions.assertEquals(2, ratingMap.get(5));
    }
}
