package com.anntly.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum ExceptionEnum {
    FOODS_NOT_FOUND(404,"菜品信息未找到"),
    NOT_FOUND(404,"未找到信息"),
    PARAMETER_ERROR(400,"参数不合法"),
    INVALID_FILE_TYPE(400,"文件类型错误"),
    SAVE_FAILED(500,"数据添加失败"),
    UPDATE_FAILED(500,"数据更新失败"),
    DELETE_FAILED(500,"数据删除失败"),
    FILE_UPLOAD_ERROR(500,"文件上传失败")
    ;

    private int code;

    private String msg;
}
