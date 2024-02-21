// package com.github.khangzxrr.domain;

// import static com.github.khangzxrr.domain.RequestBidTestSamples.*;
// import static com.github.khangzxrr.domain.RequestTestSamples.*;
// import static org.assertj.core.api.Assertions.assertThat;

// import com.github.khangzxrr.web.rest.TestUtil;
// import org.junit.jupiter.api.Test;

// class RequestBidTest {

//     @Test
//     void equalsVerifier() throws Exception {
//         TestUtil.equalsVerifier(RequestBid.class);
//         RequestBid requestBid1 = getRequestBidSample1();
//         RequestBid requestBid2 = new RequestBid();
//         assertThat(requestBid1).isNotEqualTo(requestBid2);

//         requestBid2.setId(requestBid1.getId());
//         assertThat(requestBid1).isEqualTo(requestBid2);

//         requestBid2 = getRequestBidSample2();
//         assertThat(requestBid1).isNotEqualTo(requestBid2);
//     }

//     @Test
//     void requestTest() throws Exception {
//         RequestBid requestBid = getRequestBidRandomSampleGenerator();
//         Request requestBack = getRequestRandomSampleGenerator();

//         requestBid.setRequest(requestBack);
//         assertThat(requestBid.getRequest()).isEqualTo(requestBack);

//         requestBid.request(null);
//         assertThat(requestBid.getRequest()).isNull();
//     }
// }
