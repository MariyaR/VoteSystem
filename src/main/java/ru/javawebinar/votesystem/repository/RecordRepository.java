package ru.javawebinar.votesystem.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.javawebinar.votesystem.model.Record;

import java.util.List;
import java.util.Optional;

import static ru.javawebinar.votesystem.util.ValidationUtil.checkNotFoundWithId;

@Repository
public class RecordRepository implements ru.javawebinar.votesystem.repository.Repository<Record> {

    @Autowired
    private CrudRecordRepository crudRecordRepository;

    @Transactional
    public Record save(Record record, int userId) {
        Assert.notNull(record, "record must not be null");
        if (!record.isNew()) {
            return null;
        }
        return crudRecordRepository.save(record);
    }

    public void delete(int id, int userId) {
        checkNotFoundWithId(crudRecordRepository.delete(id, userId), id);
    }


    public Record get(int id, int userId) {
        return checkNotFoundWithId(crudRecordRepository.findById(id).filter(record -> record.getUser().getId() == userId).orElse(null), id);
    }

    public List<Record> getAllEntries(int userId) {
        return crudRecordRepository.getAll(userId);
    }

    public Optional<Record> getWithUser(int id, int userId) {
        return crudRecordRepository.getWithUser(id, userId);
    }

    public Optional<Record> getUserVote(int userId) {
        return crudRecordRepository.getUserVote(userId);
    }

    public List<Record> getTodayRecords() {
        return crudRecordRepository.getTodayRecords();
    }
}
