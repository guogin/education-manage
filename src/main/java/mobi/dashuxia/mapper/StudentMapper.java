package mobi.dashuxia.mapper;

import java.util.List;

import mobi.dashuxia.domain.Student;

public interface StudentMapper {
    
    Student findById(String id);

    List<Student> findAll();
    
    List<Student> search(String[] keywords);
    
    Student save(Student student);
    
    Integer update(Student student);
}
