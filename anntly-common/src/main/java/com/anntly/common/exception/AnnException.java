package com.anntly.common.exception;

import com.anntly.common.enums.ExceptionEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AnnException extends RuntimeException{

    private ExceptionEnum exceptionEnum;
}
