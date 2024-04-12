package edu.kirkwood.learnx.data;

import edu.kirkwood.learnx.models.Course;
import edu.kirkwood.learnx.models.CourseCategory;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CourseDAO extends Database{

    public static void main(String[] args) {
        List<Course> courses = get(5, 0, "", "");
        courses.forEach(System.out::println);
    }

    public static List<Course> get(int limit, int offset, String categories, String skillLevel) {
        List<Course> courses = new ArrayList<>();
        try(Connection connection = getConnection();
            CallableStatement statement = connection.prepareCall("{CALL sp_get_courses(?, ?,?,?)}");
        ) {
            statement.setInt(1, limit);
            statement.setInt(2, offset);
            statement.setString(3, categories);
            statement.setString(4, skillLevel);
            try(ResultSet resultSet = statement.executeQuery()) {
                while(resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    String description = resultSet.getString("description");
                    String level = resultSet.getString("level");
                    String picture = resultSet.getString("picture");
                    String teacherFirstName = resultSet.getString("first_name");
                    String teacherLastName = resultSet.getString("last_name");
                    int categoryId = resultSet.getInt("category_id");
                    String categoryName = resultSet.getString("category_name");
                    Course course = new Course(id, name, description, level, picture, teacherFirstName, teacherLastName, categoryId, categoryName);
                    courses.add(course);
                }
            }
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return courses;
    }

    public static List<CourseCategory> getAllCategories() {
        List<CourseCategory> categories = new ArrayList();
        try(Connection connection = getConnection();
        CallableStatement statement = connection.prepareCall("{CALL sp_get_all_course_categories()}");
        ResultSet resultSet = statement.executeQuery();
        ) {
            while(resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int numCourses = resultSet.getInt("num_courses");
                categories.add(new CourseCategory(id, name, numCourses));
            }
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return categories;
    }

    public static boolean enroll(int userId, int courseId) {
        try (Connection connection = getConnection();
             CallableStatement statement = connection.prepareCall("{CALL sp_add_enrollment(?, ?)}"))
        {
            statement.setInt(1, userId);
            statement.setInt(2, courseId);
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public static Map<Course, Instant> getCourseEnrollments(int limit, int offset, int userId) {
        Map<Course, Instant> enrollments = new HashMap<>();
        try(Connection connection = getConnection();
        CallableStatement statement = connection.prepareCall("{CALL sp_get_courses_by_student(?, ?, ?)}");
        ) {
            statement.setInt(1, limit);
            statement.setInt(2, offset);
            statement.setInt(3, userId);
            try(ResultSet resultSet = statement.executeQuery()) {
                while(resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    String description = resultSet.getString("description");
                    String level = resultSet.getString("level");
                    String picture = resultSet.getString("picture");
                    String teacherFirstName = resultSet.getString("first_name");
                    String teacherLastName = resultSet.getString("last_name");
                    int categoryId = resultSet.getInt("category_id");
                    String categoryName = resultSet.getString("category_name");
                    Instant enrollment_date = resultSet.getTimestamp("enrollment_date").toInstant();
                    Course course = new Course(id, name, description, level, picture,
                            teacherFirstName, teacherLastName, categoryId, categoryName);
                    enrollments.put(course, enrollment_date);
                }
            }
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return enrollments;
    }


}
