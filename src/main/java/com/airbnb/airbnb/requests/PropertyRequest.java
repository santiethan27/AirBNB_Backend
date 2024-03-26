/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.airbnb.airbnb.requests;

import com.airbnb.airbnb.enums.PropertyTypes;
import com.airbnb.airbnb.enums.States;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PropertyRequest {
    private String owner;
    private States state;
    private byte[] images;
    private String description;
    private Double size;
    private String address;
    private Integer rating;
    private String postalCode;
    private String propertyTypes;
}
