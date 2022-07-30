package com.lunar.lunarMgmt.common.exception.model;

/**
 * @Package : com.lunar.lunarMgmt.common.exception.model
 * @FileName : ErrorCode.java
 * @CreateDate : 2022. 7. 25.
 * @author : JayPark
 * @Description : code는 임의로 땃음
 *
 *  L = Login
 *  A = Authorization
 *  D = Database
 *  U = User
 *  E = etc (기타)
 */

public enum ErrorCode {
  NOT_FOUND_USER(404, "L001", "사용 가능한 로그인 정보가 아닙니다."),

  EXPIRED_TOKEN(401, "A001", "만료된 토큰입니다."),
  NOT_FOUND_TOKEN(401, "A002", "유효하지 않은 토큰입니다."),
  FORBIDDEN_TOKEN(403, "A003", "접근할 수 없는 메뉴입니다."),
  NO_DATA_FOUND(404, "A004", "데이터가 존재하지 않습니다."),

  EXISTED_USER(500, "A011", "권한에 등록된 사용자가 존재합니다."),

  FOREIGN_KEY_CONSTRAINT(500, "D001", "사용되어지고 있는 값을 제거 후 다시 시도해주십시오."),
  DUPLICATION(500, "D002", "중복되는 값이 존재합니다."),

  VALID_ID(500, "U001", "중복된 아이디 입니다."),
  VALID_DEPT_CODE(500, "D001", "이미 등록된 부서코드가 존재합니다."),
  VALID_DOCTOR_CODE(500, "D002", "이미 등록된 담당자 코드가 존재합니다."),
  CHECK_DEPT_CODE(500, "D003", "등록된 부서가 존재하지 않습니다."),

  VALID_HISTORY_TAKING_CODE(500, "D002", "이미 등록된 문진 항목입니다."),
  VALID_HISTORY_TAKING_DOCTOR_CODE(500, "D003", "이미 등록된 담당자입니다."),

  FILE_UPLOAD_FAIL(500, "E001", "파일 업로드에 실패하였습니다."),
  FILE_DELETE_FAIL(500, "E002", "파일 삭제에 실패하였습니다."),
  FILE_NOT_FOUND(500, "E003", "파일이 존재하지 않습니다."),

  STRAPI_MENU_NOT_FOUND(500, "E004", "스트라피 메뉴를 찾지 못하였습니다."),
  AGENT_NOT_FOUND(500, "E004", "AGENT를 찾지 못하였습니다."),
  AIME_CALL_NUM_NOT_FOUND(500, "E006", "AIME 전화 정보를 찾지 못하였습니다."),
  MACHINE_LEARNING_APPY_FAIL(500, "M001", "머신러닝 적용 실패하였습니다.");

  private int status;
  private String code;
  private String message;

  public String getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }

  public int getStatus() {
    return status;
  }

  ErrorCode(int status, String code, String message) {
    this.status = status;
    this.code = code;
    this.message = message;
  }
}
