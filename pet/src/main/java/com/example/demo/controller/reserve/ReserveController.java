package com.example.demo.controller.reserve;

import com.example.demo.domain.entity.OwnerEntity;
import com.example.demo.domain.entity.SitterEntity;
import com.example.demo.service.OwnerServiceImpl;
import com.example.demo.service.SitterServiceImpl;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Controller
@Slf4j
public class ReserveController {

    @Autowired
    private OwnerServiceImpl ownerService;

    @Autowired
    private SitterServiceImpl sitterService;

    String RESTAPI_KEY = "4881768043204435";
    String RESTAPI_SECRET = "qX6baW4APJB91uMknRd8DcE6OUs2XGybSfx8thVQaAtKCMTlEzRLSlbzGBFTEuO54irUsTik9uieDbgn";
    private PortOneTokenResponse portOneTokenResponse;

    @GetMapping("/getToken")
    @ResponseBody
    public void getToken() {
        log.info("GET /portOne/getToken....");
        //요청 정보 확인
        String url = "https://api.iamport.kr/users/getToken";
        //요청 헤더 설정
        HttpHeaders header = new HttpHeaders();
//        header.add("Content-Type","application/json"); //form x
        //요청 바디 설정
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("imp_key", RESTAPI_KEY);
        params.add("imp_secret", RESTAPI_SECRET);

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params, header);

        //요청 후 응답확인
        RestTemplate rt = new RestTemplate();
        ResponseEntity<PortOneTokenResponse> response =
                rt.exchange(url, HttpMethod.POST, entity, PortOneTokenResponse.class);
        System.out.println(response.getBody());
        this.portOneTokenResponse = response.getBody();

    }

    @GetMapping("/reserve")
    public String reserve(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        OwnerEntity owner = ownerService.findByUserEmail(userDetails.getUsername());
        model.addAttribute("owner", owner);

        List<SitterEntity> sitterList = sitterService.getAllSitters();
        model.addAttribute("sitters", sitterList);

        return "reserve/reserve";
    }

    //   *** 임시 URL 기능 구현시 삭제 필수 !!! ***
    @GetMapping("/reserve/confirm")
    public String reserveSuccess() {
        return "reserve/reserveConfirm";
    }

    //결제조회(다건조회)
    @GetMapping("/reserve/payments")
    public void payments() {
        log.info("GET /portOne/getPayments...");
        String url = "https://api.iamport.kr/payments?imp_uid[]=imp_979464507864&merchant_uid[]=";
        //요청 헤더 설정
        HttpHeaders header = new HttpHeaders();
        header.add("Authorization", "Bearer " + this.portOneTokenResponse.getResponse().getAccess_token());
        header.add("Content-Type", "application/json");

        HttpEntity entity = new HttpEntity<>(header);

        //요청 후 응답확인
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response =
                rt.exchange(url, HttpMethod.GET, entity, String.class);

        System.out.println(response);
    }

    //인증조회
    @Data
    private static class Response {
        public String access_token;
        public int now;
        public int expired_at;
    }

    @Data
    private static class PortOneTokenResponse {
        public int code;
        public Object message;
        public Response response;
    }

}
