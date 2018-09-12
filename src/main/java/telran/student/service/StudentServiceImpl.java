package telran.student.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import telran.student.dao.IStudentRepository;
import telran.student.dto.ScoreDto;
import telran.student.dto.StudentDto;
import telran.student.dto.StudentEditDto;
import telran.student.dto.StudentResponseDto;
import telran.student.entities.Student;

@Service
public class StudentServiceImpl implements IStudentService {
	
	@Autowired
	IStudentRepository studentRepository;

	@Override
	public boolean addStudent(StudentDto student) {
		Student newStudent = new Student(student.getId(), 
				student.getName(), student.getPassword());
		return studentRepository.addStudent(newStudent);
	}

	@Override
	public StudentResponseDto deleteStudent(int id) {
		StudentResponseDto srd = new StudentResponseDto();
		Student student = studentRepository.removeStudent(id);
		if(student != null) {
			srd.setId(student.getId());
			srd.setName(student.getName());
			srd.setScores(student.getScores());
		}
		return srd;
	}

	@Override
	public StudentDto editStudent(int id, StudentEditDto student) {
		Student stu = studentRepository.getStudentById(id);
		StudentDto sDto = new StudentDto();
		if(stu != null) {
		String name = student.getName();
		String password = student.getPassword();
		stu.setName(name);
		stu.setPassword(password);
		studentRepository.editStudent(stu);
		sDto.setName(name);
		sDto.setPassword(password);
		sDto.setId(id);
		}
		return sDto;
	}

	@Override
	public StudentResponseDto getStudent(int id) {
		Student student = studentRepository.getStudentById(id);
		StudentResponseDto studentResponse = new StudentResponseDto();
		studentResponse.setId(student.getId());
		studentResponse.setName(student.getName());
		studentResponse.setScores(student.getScores());
		return studentResponse;
	}

	@Override
	public boolean addScore(int id, ScoreDto score) {
		
		Student student = studentRepository.getStudentById(id);
		if(student != null) {
			student.getScores().put(score.getExamName(), score.getScore());
		}else {
			return false;
		}
		return true;
	}

}
