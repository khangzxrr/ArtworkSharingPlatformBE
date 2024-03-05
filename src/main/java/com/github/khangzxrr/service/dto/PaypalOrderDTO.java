package com.github.khangzxrr.service.dto;

import java.io.Serializable;
import java.util.Set;

public class PaypalOrderDTO implements Serializable {

    private String id;
    private String status;
    private Set<PaypalOrderLinkDTO> links;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Set<PaypalOrderLinkDTO> getLinks() {
        return links;
    }

    public void setLinks(Set<PaypalOrderLinkDTO> links) {
        this.links = links;
    }
}
