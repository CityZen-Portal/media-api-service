package com.example.image.response;



import com.example.image.enumeration.ResponseStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonResponse {
   private String message;
   private ResponseStatus status;
   private Object data;
   private int statusCode;


}
