import javax.swing.*;
import java.awt.*;

public class SchoolMenuFinal {
    public static void main(String[] args) {

        JFrame frame = new JFrame("School Management");
        JMenuBar menuBar = new JMenuBar();

        ImageIcon stuIcon = new ImageIcon("student.png");
        stuIcon = new ImageIcon(stuIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
        JMenu students = new JMenu("Students");
        students.setIcon(stuIcon);
        students.setMnemonic('S');
        students.setToolTipText("Manage students");

        ImageIcon addIcon = new ImageIcon("add.png");
        addIcon = new ImageIcon(addIcon.getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH));
        JMenuItem addStudent = new JMenuItem("Add Student", addIcon);

        ImageIcon editIcon = new ImageIcon("edit.png");
        editIcon = new ImageIcon(editIcon.getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH));
        JMenuItem editStudent = new JMenuItem("Edit Student", editIcon);

        ImageIcon delIcon = new ImageIcon("delete.png");
        delIcon = new ImageIcon(delIcon.getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH));
        JMenuItem deleteStudent = new JMenuItem("Delete Student", delIcon);

        ImageIcon searchIcon = new ImageIcon("search.png");
        searchIcon = new ImageIcon(searchIcon.getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH));
        JMenuItem searchStudent = new JMenuItem("Search Student", searchIcon);

        ImageIcon viewIcon = new ImageIcon("view.png");
        viewIcon = new ImageIcon(viewIcon.getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH));
        JMenuItem viewStudents = new JMenuItem("View Students", viewIcon);

        ImageIcon moreIcon = new ImageIcon("more.png");
        moreIcon = new ImageIcon(moreIcon.getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH));
        JMenu moreOptions = new JMenu("More Options");
        moreOptions.setIcon(moreIcon);

        JMenuItem reports = new JMenuItem("Reports");

        moreOptions.add(reports);

        students.add(addStudent);
        students.add(editStudent);
        students.add(deleteStudent);
        students.add(searchStudent);
        students.add(viewStudents);
        students.add(moreOptions);

        ImageIcon teacherIcon = new ImageIcon("teacher.png");
        teacherIcon = new ImageIcon(teacherIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
        JMenu teachers = new JMenu("Teachers");
        teachers.setIcon(teacherIcon);
        teachers.setMnemonic('T');
        teachers.setToolTipText("Manage teachers");

        teachers.add(new JMenuItem("Add Teacher"));
        teachers.add(new JMenuItem("Edit Teacher"));
        teachers.add(new JMenuItem("Delete Teacher"));
        teachers.add(new JMenuItem("Assign Class"));
        teachers.add(new JMenuItem("View Teachers"));

        ImageIcon classIcon = new ImageIcon("classes.png");
        classIcon = new ImageIcon(classIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
        JMenu classes = new JMenu("Classes");
        classes.setIcon(classIcon);
        classes.setMnemonic('C');
        classes.setToolTipText("Manage classes");

        classes.add(new JMenuItem("Create Class"));
        classes.add(new JMenuItem("Manage Sections"));
        classes.add(new JMenuItem("Assign Teacher"));
        classes.add(new JMenuItem("Timetable"));
        classes.add(new JMenuItem("View Classes"));

        ImageIcon examIcon = new ImageIcon("exam.png");
        examIcon = new ImageIcon(examIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
        JMenu exams = new JMenu("Exams");
        exams.setIcon(examIcon);
        exams.setMnemonic('E');
        exams.setToolTipText("Manage exams");

        exams.add(new JMenuItem("Create Exam"));
        exams.add(new JMenuItem("Enter Marks"));
        exams.add(new JMenuItem("Update Marks"));
        exams.add(new JMenuItem("Exam Schedule"));
        exams.add(new JMenuItem("Exam Results"));

        ImageIcon reportIcon = new ImageIcon("report.png");
        reportIcon = new ImageIcon(reportIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
        JMenu reportsMenu = new JMenu("Reports");
        reportsMenu.setIcon(reportIcon);
        reportsMenu.setMnemonic('R');
        reportsMenu.setToolTipText("View reports");

        reportsMenu.add(new JMenuItem("Student Report"));
        reportsMenu.add(new JMenuItem("Teacher Report"));
        reportsMenu.add(new JMenuItem("Attendance Report"));
        reportsMenu.add(new JMenuItem("Performance Report"));
        reportsMenu.add(new JMenuItem("Class Strength"));

        menuBar.add(students);
        menuBar.add(teachers);
        menuBar.add(classes);
        menuBar.add(exams);
        menuBar.add(reportsMenu);

        frame.setJMenuBar(menuBar);
        frame.setSize(500, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
 
