package com.github.khangzxrr.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class RequestAttachmentTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static RequestAttachment getRequestAttachmentSample1() {
        return new RequestAttachment().id(1L);
    }

    public static RequestAttachment getRequestAttachmentSample2() {
        return new RequestAttachment().id(2L);
    }

    public static RequestAttachment getRequestAttachmentRandomSampleGenerator() {
        return new RequestAttachment().id(longCount.incrementAndGet());
    }
}
