package br.com.thenotice.domain.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class GenericData<T> {
    private HttpStatus status;
    private String msg;
    public T data;
}
