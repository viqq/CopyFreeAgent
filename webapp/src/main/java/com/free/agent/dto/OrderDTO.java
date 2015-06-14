package com.free.agent.dto;

public class OrderDTO {

    private Integer filmId;

    private String customerName;

    private String customerPhone;

    public OrderDTO() {
    }

    public OrderDTO(Integer filmId, String customerName, String customerPhone) {
        this.filmId = filmId;
        this.customerName = customerName;
        this.customerPhone = customerPhone;
    }

    public Integer getFilmId() {
        return filmId;
    }

    public void setFilmId(Integer filmId) {
        this.filmId = filmId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

}
