package swp.studentprojectportal.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

@Configuration
public class configThymeleafDirectory {
    @Bean
    public String title() {
        return "Student project portal";
    }

    @Bean
    public int adminRoleId() {return 2;}

    @Bean
    public int subjectManagerRoleId() {return 3;}

    @Bean
    public int teacherRoleId() {return 4;}

    @Bean
    public int userRoleId() {return 1;}

    @Bean
    public int classManagerRoleId() {return 4;}

    @Bean
    public ClassLoaderTemplateResolver commonTemplateResolver() {
        ClassLoaderTemplateResolver yourTemplateResolver = new ClassLoaderTemplateResolver();
        yourTemplateResolver.setPrefix("templates/common/");
        yourTemplateResolver.setSuffix(".html");
        yourTemplateResolver.setTemplateMode(TemplateMode.HTML);
        yourTemplateResolver.setCharacterEncoding("UTF-8");
        yourTemplateResolver.setOrder(0); // this is iportant. This way springboot will listen to both places 0 and 1
        yourTemplateResolver.setCheckExistence(true);

        return yourTemplateResolver;
    }

    @Bean
    public ClassLoaderTemplateResolver adminSettingTemplateResolver() {
        ClassLoaderTemplateResolver yourTemplateResolver = new ClassLoaderTemplateResolver();
        yourTemplateResolver.setPrefix("templates/admin/setting/");
        yourTemplateResolver.setSuffix(".html");
        yourTemplateResolver.setTemplateMode(TemplateMode.HTML);
        yourTemplateResolver.setCharacterEncoding("UTF-8");
        yourTemplateResolver.setOrder(1); // this is iportant. This way springboot will listen to both places 0 and 1
        yourTemplateResolver.setCheckExistence(true);

        return yourTemplateResolver;
    }

    @Bean
    public ClassLoaderTemplateResolver adminSubjectTemplateResolver() {
        ClassLoaderTemplateResolver yourTemplateResolver = new ClassLoaderTemplateResolver();
        yourTemplateResolver.setPrefix("templates/admin/subject/");
        yourTemplateResolver.setSuffix(".html");
        yourTemplateResolver.setTemplateMode(TemplateMode.HTML);
        yourTemplateResolver.setCharacterEncoding("UTF-8");
        yourTemplateResolver.setOrder(2); // this is iportant. This way springboot will listen to both places 0 and 1
        yourTemplateResolver.setCheckExistence(true);

        return yourTemplateResolver;
    }

    @Bean
    public ClassLoaderTemplateResolver adminUserTemplateResolver() {
        ClassLoaderTemplateResolver yourTemplateResolver = new ClassLoaderTemplateResolver();
        yourTemplateResolver.setPrefix("templates/admin/user/");
        yourTemplateResolver.setSuffix(".html");
        yourTemplateResolver.setTemplateMode(TemplateMode.HTML);
        yourTemplateResolver.setCharacterEncoding("UTF-8");
        yourTemplateResolver.setOrder(3); // this is iportant. This way springboot will listen to both places 0 and 1
        yourTemplateResolver.setCheckExistence(true);

        return yourTemplateResolver;
    }

    @Bean
    public ClassLoaderTemplateResolver componentTemplateResolver() {
        ClassLoaderTemplateResolver yourTemplateResolver = new ClassLoaderTemplateResolver();
        yourTemplateResolver.setPrefix("templates/component/");
        yourTemplateResolver.setSuffix(".html");
        yourTemplateResolver.setTemplateMode(TemplateMode.HTML);
        yourTemplateResolver.setCharacterEncoding("UTF-8");
        yourTemplateResolver.setOrder(4); // this is iportant. This way springboot will listen to both places 0 and 1
        yourTemplateResolver.setCheckExistence(true);

        return yourTemplateResolver;
    }

    @Bean
    public ClassLoaderTemplateResolver subjectManagerSubjectSettingTemplateResolver() {
        ClassLoaderTemplateResolver yourTemplateResolver = new ClassLoaderTemplateResolver();
        yourTemplateResolver.setPrefix("templates/subject_manager/subject_setting/");
        yourTemplateResolver.setSuffix(".html");
        yourTemplateResolver.setTemplateMode(TemplateMode.HTML);
        yourTemplateResolver.setCharacterEncoding("UTF-8");
        yourTemplateResolver.setOrder(1); // this is iportant. This way springboot will listen to both places 0 and 1
        yourTemplateResolver.setCheckExistence(true);

        return yourTemplateResolver;
    }

    @Bean
    public ClassLoaderTemplateResolver authenTemplateResolver() {
        ClassLoaderTemplateResolver yourTemplateResolver = new ClassLoaderTemplateResolver();
        yourTemplateResolver.setPrefix("templates/authentication/");
        yourTemplateResolver.setSuffix(".html");
        yourTemplateResolver.setTemplateMode(TemplateMode.HTML);
        yourTemplateResolver.setCharacterEncoding("UTF-8");
        yourTemplateResolver.setOrder(1); // this is iportant. This way springboot will listen to both places 0 and 1
        yourTemplateResolver.setCheckExistence(true);

        return yourTemplateResolver;
    }

    @Bean
    public ClassLoaderTemplateResolver projectClassManagerTemplateResolver() {
        ClassLoaderTemplateResolver yourTemplateResolver = new ClassLoaderTemplateResolver();
        yourTemplateResolver.setPrefix("templates/class_manager/project/");
        yourTemplateResolver.setSuffix(".html");
        yourTemplateResolver.setTemplateMode(TemplateMode.HTML);
        yourTemplateResolver.setCharacterEncoding("UTF-8");
        yourTemplateResolver.setOrder(1); // this is iportant. This way springboot will listen to both places 0 and 1
        yourTemplateResolver.setCheckExistence(true);

        return yourTemplateResolver;
    }

    @Bean
    public ClassLoaderTemplateResolver ClassSettingTemplateResolver() {
        ClassLoaderTemplateResolver yourTemplateResolver = new ClassLoaderTemplateResolver();
        yourTemplateResolver.setPrefix("templates/class_manager/");
        yourTemplateResolver.setSuffix(".html");
        yourTemplateResolver.setTemplateMode(TemplateMode.HTML);
        yourTemplateResolver.setCharacterEncoding("UTF-8");
        yourTemplateResolver.setOrder(1); // this is iportant. This way springboot will listen to both places 0 and 1
        yourTemplateResolver.setCheckExistence(true);

        return yourTemplateResolver;
    }

    @Bean
    public ClassLoaderTemplateResolver ProjectMentorTemplateResolver() {
        ClassLoaderTemplateResolver yourTemplateResolver = new ClassLoaderTemplateResolver();
        yourTemplateResolver.setPrefix("templates/project_mentor/");
        yourTemplateResolver.setSuffix(".html");
        yourTemplateResolver.setTemplateMode(TemplateMode.HTML);
        yourTemplateResolver.setCharacterEncoding("UTF-8");
        yourTemplateResolver.setOrder(1); // this is iportant. This way springboot will listen to both places 0 and 1
        yourTemplateResolver.setCheckExistence(true);

        return yourTemplateResolver;
    }
}
