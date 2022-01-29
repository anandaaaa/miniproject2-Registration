package in.myproject.anand.Exceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import in.myproject.anand.Exception.AppError;
import in.myproject.anand.Exception.RegAppException;

@RestControllerAdvice
public class RegAppExceptionHandler {
	
	@ExceptionHandler(value=RegAppException.class)
	public ResponseEntity<AppError> handleException(RegAppException regAppException)
	{
		AppError appError=new AppError();
		appError.setErrorCode("ERR1");
		appError.setErrorMsg(regAppException.getMessage());
		
		ResponseEntity<AppError> responseEntity = new ResponseEntity<AppError>(appError,HttpStatus.INTERNAL_SERVER_ERROR);
		return responseEntity;
	}

}
