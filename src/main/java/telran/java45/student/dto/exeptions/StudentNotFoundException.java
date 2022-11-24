package telran.java45.student.dto.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class StudentNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1122088642376957512L;
	
	public StudentNotFoundException() {
		
	}
	
	public StudentNotFoundException(int id) {
		super("Student with id" + id + "not found");
	}
	

}
