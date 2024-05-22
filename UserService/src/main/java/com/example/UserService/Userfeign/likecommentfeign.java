package com.example.UserService.Userfeign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("LIKCOMMENTCONFIG")
public interface likecommentfeign {
    
   @DeleteMapping("/com_like/All")
    public ResponseEntity<Boolean> deletelikescomments(@PathVariable Long deleteid);

}
