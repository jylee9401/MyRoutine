package com.example.Myroutine;


import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;

@Service
public class RoutineService {

    private final RoutineRepository routineRepository;

    public RoutineService(RoutineRepository routineRepository) {
        this.routineRepository = routineRepository;
    }

    public List<Routine> findByStatus(String status, User user) {
        List<Routine> filtered = new ArrayList<>();
        List<Routine> userRoutines = routineRepository.findByUser(user);

        if ("done".equals(status)) {
            for (Routine r : userRoutines) {
                if (r.isDone()) {
                    filtered.add(r);
                }
            }
        } else if ("undone".equals(status)) {
            for (Routine r : userRoutines) {
                if (!r.isDone()) {
                    filtered.add(r);
                }
            }
        } else if ("today".equals(status)) {
            for (Routine r : userRoutines) {
                if (r.getDate().equals(LocalDate.now())) {
                    filtered.add(r);
                }
            }
        } else {
            filtered.addAll(userRoutines);
        }
        filtered.sort(Comparator.comparing(Routine::getDate));
        return filtered;
    }

    public void addRoutine(String title,
                           String description,
                           LocalDate date,
                           User user) {
        if (title == null || title.trim().isEmpty()) {
            return;
        }

        Routine routine = new Routine(
                null,
                title,
                description,
                date,
                user);
        routineRepository.save(routine);
    }

    public void deleteRoutine(Long id) {
        routineRepository.deleteById(id);
    }

    public void toggleRoutine(Long id) {
        Routine routine = routineRepository.findById(id).orElse(null);

        if (routine != null) {
            routine.toggleDone();
            routineRepository.save(routine);
        }
    }

    public Routine findRoutine(Long id) {
        return routineRepository.findById(id).orElse(null);
    }

    public void updateRoutine(Long id,
                              String title,
                              String description,
                              LocalDate date) {
        if (title == null || title.trim().isEmpty()) {
            return;
        }

        Routine routine = routineRepository.findById(id).orElse(null);
        if (routine != null) {
            routine.setTitle(title);
            routine.setDescription(description);
            routine.setDate(date);
            routineRepository.save(routine);
        }
    }

    public int getCompletionRate(User user) {
        List<Routine> routines = routineRepository.findByUser(user);

        if (routines.isEmpty()) {
            return 0;
        }

        int doneCount = 0;

        for (Routine routine : routines) {
            if (routine.isDone()) {
                doneCount++;
            }
        }
        return doneCount * 100 / routines.size();
    }

    public List<Routine> searchByKeyword(String keyword, User user) {
        List<Routine> result = new ArrayList<>();
        List<Routine> userRoutines = routineRepository.findByUser(user);

        if (keyword == null || keyword.trim().isEmpty()) {
            result.addAll(userRoutines);
            result.sort(Comparator.comparing(Routine::getDate));
            return result;
        }

        for (Routine routine : userRoutines) {
            if (routine.getTitle().contains(keyword)
                    || routine.getDescription().contains(keyword)) {
                result.add(routine);
            }
        }
        result.sort(Comparator.comparing(Routine::getDate));

        return result;
    }

    public boolean isOwner(Long routineId, User user) {
        Routine routine = routineRepository.findById(routineId).orElse(null);

        if (routine == null) {
            return false;
        }
        if (routine.getUser() == null) {
            return false;
        }

        return routine.getUser().getId().equals(user.getId());
    }
}