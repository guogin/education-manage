package mobi.dashuxia.service;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.Future;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.NoResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import mobi.dashuxia.domain.Student;
import mobi.dashuxia.mapper.StudentMapper;

@Service
@Transactional(propagation = Propagation.SUPPORTS,
    readOnly = true)
public class StudentService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private StudentMapper studentMapper;

    @Async
    public Future<Void> execute() {
        return new AsyncResult<Void>(null);
    }

    @Async
    public Future<Student> findOne(String category_id) {
        logger.info("> findOne");
        return new AsyncResult<Student>(studentMapper.findById(category_id));
    }

    @SuppressWarnings("rawtypes")
    @Async
    public Future<PageInfo> findAll(Integer page, Integer size) {
        logger.info("> Find All page:" + page + ", size: " + size);

        PageHelper.startPage(page, size);
        PageHelper.orderBy("id desc");
        List<Student> collection = studentMapper.findAll();
        PageInfo<Student> pageInfo = new PageInfo<Student>(collection);
        return new AsyncResult<PageInfo>(pageInfo);
    }

    @SuppressWarnings("rawtypes")
    @Async
    public Future<PageInfo> search(String keys, Integer page, Integer size) {
        logger.info("> Search...");
        String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(keys);
        String[] keywords = m.replaceAll("").trim().split(" ");
        PageHelper.startPage(page, size);
        PageInfo<Student> pageInfo = new PageInfo<Student>(studentMapper.search(
                keywords));
        return new AsyncResult<PageInfo>(pageInfo);
    }

    @Async
    @Transactional(propagation = Propagation.REQUIRED,
        readOnly = false)
    public Future<Integer> update(Student student) {
        Student categoryPersisted = studentMapper.findById(student.getId());

        if (categoryPersisted == null) {
            // Cannot update Category than hasn't been persisted.
            throw new NoResultException("Can't find the entity with id="
                    + student.getId());
        }

        Integer rows = studentMapper.update(student);
        return new AsyncResult<Integer>(rows);
    }
}
