package vn.vissoft.ems.core.repo;

import vn.vissoft.ems.core.dto.ActivityDTO;
import vn.vissoft.ems.core.dto.ProgressDTO;
import vn.vissoft.ems.core.helper.Util;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class ProjectDao {

  static Integer[] issues = new Integer[Util.MONTHS.length];
  static Integer[] tasks = new Integer[Util.MONTHS.length];
  static String line = "Send payment today,Post Banner on official website, Meeting with BD team, Setting Github repository, Calling manager, Book flight for business trip";
  static String[] todoList = line.split(", ");
  static List<ActivityDTO> recentActivityList = new LinkedList<>();
  static List<ActivityDTO> activityList = new LinkedList<>();
  static List<ProgressDTO> progressSummary = new LinkedList<>();
  static String[] TITLES = {"This week", "Overdue", "Issues", "Features"};
  static String[] TOPICS = {"Due Tasks", "Tasks", "Open", "Proposals"};

  static String[] NAMES = {"Matthew", "Lucas", "John", "Peter", "Eric", "Kevin", "Grace", "Paul"};
  static String[] TASKS = {"draft design", "study GDPR", "release product", "layout pages",
      "main page", "help others", "packaging products"};
  static String[] PROJECTS = {"MedicalPro", "Real Home", "Digital Agency", "Elite Force"};

  static {
    for (int i = 0; i < Util.MONTHS.length; i++) {
      issues[i] = Util.nextInt(0, 50);
    }

    for (int i = 0; i < Util.MONTHS.length; i++) {
      tasks[i] = Util.nextInt(0, 50);
    }

    for (int i = 0; i < 4; i++) {
      ActivityDTO activity = new ActivityDTO();
      activity.setOwner(NAMES[i]);
      activity.setTask(TASKS[i]);
      activity.setProject(PROJECTS[i]);
      activity.setCompletedTime(toDate(LocalDateTime.now().minusDays(Util.nextInt(1, 10))));
      recentActivityList.add(activity);
    }
    for (int i = 0; i < 5; i++) {
      ActivityDTO activity = new ActivityDTO();
      activity.setOwner(NAMES[i]);
      activity.setTask(TASKS[i]);
      activity.setProject(PROJECTS[i % PROJECTS.length]);
      activity.setDueDate(toDate(LocalDateTime.now().plusDays(Util.nextInt(1, 10))));
      activityList.add(activity);
    }
    initProgressSummary();
  }

  private static void initProgressSummary() {
    for (int i = 0; i < 4; i++) {
      ProgressDTO p = new ProgressDTO();
      p.setTitle(TITLES[i]);
      p.setTopic(TOPICS[i]);
      p.setIncompleted(Util.nextInt(10, 20));
      p.setCompleted(Util.nextInt(0, 10));
      progressSummary.add(p);
    }
  }

  public static Date toDate(LocalDateTime dateTime) {
    return java.sql.Timestamp.valueOf(dateTime);
  }

  public static Integer[] queryIssues() {
    return issues;
  }

  public static Integer[] queryTasks() {
    return tasks;
  }

  public static String[] getTodoList() {
    return todoList;
  }

  public static List<ActivityDTO> getRecentActivityList() {
    return recentActivityList;
  }

  public static List<ActivityDTO> getActivityList() {
    return activityList;
  }

  public static List<ProgressDTO> getProgressSummary() {
    return progressSummary;
  }
}
