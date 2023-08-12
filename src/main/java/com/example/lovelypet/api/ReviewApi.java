package com.example.lovelypet.api;

import com.example.lovelypet.business.ReviewBusiness;
import com.example.lovelypet.entity.Review;
import com.example.lovelypet.exception.BaseException;
import com.example.lovelypet.model.ReviewRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/review")
public class ReviewApi {

    private final ReviewBusiness reviewBusiness;

    public ReviewApi(ReviewBusiness reviewBusiness) {
        this.reviewBusiness = reviewBusiness;
    }

    @PostMapping("/review")
    public ResponseEntity<String> review(@RequestBody ReviewRequest request) throws BaseException {
        String response = reviewBusiness.satisfaction(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/list-review")
    public ResponseEntity<List<Review>> listFeedback(@RequestParam int id) throws BaseException {
        List<Review> response = reviewBusiness.listReview(id);
        return ResponseEntity.ok(response);
    }

    ////////////////////////////////
    /////////////////////////////////////
}
