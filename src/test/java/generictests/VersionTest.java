package generictests;

import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringBootVersion;
import org.springframework.boot.system.JavaVersion;
import org.springframework.core.SpringVersion;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VersionTest {

    @Test
    void versionTest(){
        assertEquals(SpringVersion.getVersion(), "6.1.2");
        assertEquals(SpringBootVersion.getVersion(), "3.2.1");
        assertEquals(JavaVersion.getJavaVersion(), JavaVersion.SEVENTEEN);
    }
}
