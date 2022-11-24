package telran.java45.student.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import telran.java45.student.dao.StudentRepository;
import telran.java45.student.dto.StudentCreateDto;
import telran.java45.student.dto.StudentDto;
import telran.java45.student.dto.StudentScoreDto;
import telran.java45.student.dto.StudentUpdateDto;
import telran.java45.student.dto.exeptions.StudentNotFoundException;
import telran.java45.student.model.Student;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

	final StudentRepository studentRepository;
	final ModelMapper modelMapper;

	@Override
	public Boolean addStudent(StudentCreateDto studentCreateDto) {
		if (studentRepository.findById(studentCreateDto.getId()).isPresent()) {
			return false;
		}
//		Student student = new Student(studentCreateDto.getId(), studentCreateDto.getName(),
//				studentCreateDto.getPassword());
//		studentRepository.save(student);
        Student student = modelMapper.map(studentCreateDto, Student.class);
        studentRepository.save(student);
		return true;
	}

	@Override
	public StudentDto findStudent(Integer id) {
		Student student = studentRepository.findById(id)
				.orElseThrow(() -> new StudentNotFoundException(id));
//		return new StudentDto(id, student.getName(), student.getScores());
		return modelMapper.map(student, StudentDto.class);
	}

	@Override
	public StudentDto removeStudent(Integer id) {
		Student student = studentRepository.findById(id)
				.orElseThrow(() -> new StudentNotFoundException(id));
		studentRepository.deleteById(id);
//      return new StudentDto(id, student.getName(), student.getScores());		
		return modelMapper.map(student, StudentDto.class);

	}

	@Override
	public StudentCreateDto updateStudent(Integer id, StudentUpdateDto studentUpdateDto) {
		Student student = studentRepository.findById(id)
				.orElseThrow(() -> new StudentNotFoundException(id));
		if (studentUpdateDto.getName() != null) {
			student.setName(studentUpdateDto.getName());
		}
		if (studentUpdateDto.getPassword() != null) {
			student.setPassword(studentUpdateDto.getPassword());
		}
		studentRepository.save(student);
//      return StudentCreateDto.builder().id(id).name(student.getName()).password(student.getPassword()).build();		
		return modelMapper.map(student, StudentCreateDto.class);
	}

	@Override
	public Boolean addScore(Integer id, StudentScoreDto studentScoreDto) {
		Student student = studentRepository.findById(id)
				.orElseThrow(() -> new StudentNotFoundException(id));
		boolean res = student.addScore(studentScoreDto.getExamName(), studentScoreDto.getScore());
		studentRepository.save(student);
		return res;
	}

	@Override
	public List<StudentDto> findStudentsByName(String name) {
		return studentRepository.findByNameIgnoreCase(name)
//              .map(s -> new StudentDto(s.getId(), s.getName(), s.getScores()))
				.map(s -> modelMapper.map(s.getId(), StudentDto.class))
				.collect(Collectors.toList());
	}

	@Override
	public Long getStudentsNamesQuantity(List<String> names) {
		//TODO Homework
		return studentRepository.countByNameIn(names);				
	}

	@Override
	public List<StudentDto> getStudentsByExamMinScore(String exam, Integer minScore) {
		return	studentRepository.findByExamAndScoreGreatherThan(exam, minScore)
//                  .map(s -> new StudentDto(s.getId(), s.getName(), s.getScores()))				
					.map(s -> modelMapper.map(s.getId(), StudentDto.class))
					.collect(Collectors.toList());
	}

}