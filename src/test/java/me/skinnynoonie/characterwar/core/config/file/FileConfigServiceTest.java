package me.skinnynoonie.characterwar.core.config.file;

import com.google.gson.Gson;
import me.skinnynoonie.characterwar.core.config.Config;
import me.skinnynoonie.characterwar.core.config.ConfigService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class FileConfigServiceTest {

    @Test
    void testExceptions_loadAndRegister() throws IOException {
        ConfigService<TestConfig> configService = setUpNewConfigService();

        assertDoesNotThrow( () -> configService.loadAndRegister(new ValidConfigWithObjects()) );
        assertDoesNotThrow( () -> configService.loadAndRegister(new ValidConfigWithPrimitives()) );
        assertThrows(IllegalStateException.class, () -> configService.loadAndRegister(new ValidConfigWithObjects()) ); // Loading an existing config.
        assertThrows(IllegalStateException.class, () -> configService.loadAndRegister(new NotValidUnAnnotatedConfig()) );
    }

    @Test
    void testFileContentIsCorrect_loadAndRegister() throws IOException {
        FileConfigService<TestConfig> configService = setUpNewConfigService();
        Gson gson = configService.getGson();
        configService.loadAndRegister(new ValidConfigWithObjects());
        configService.loadAndRegister(new ValidConfigWithPrimitives());
        String contentOfConfigWithObjects = Files.readString(configService.getConfigFolderPath().resolve("ValidConfigWithObjects.json"));
        String contentOfConfigWithPrimitives = Files.readString(configService.getConfigFolderPath().resolve("ValidConfigWithPrimitives.json"));

        assertEquals(new ValidConfigWithObjects(), gson.fromJson(contentOfConfigWithObjects, ValidConfigWithObjects.class));
        assertEquals(new ValidConfigWithPrimitives(), gson.fromJson(contentOfConfigWithPrimitives, ValidConfigWithPrimitives.class));
    }

    @Test
    void testExceptions_update() throws IOException {
        ConfigService<TestConfig> configService = setUpNewConfigService();
        configService.loadAndRegister(new ValidConfigWithObjects());
        configService.loadAndRegister(new ValidConfigWithPrimitives());

        assertDoesNotThrow(() -> configService.update(ValidConfigWithObjects.class));
        assertDoesNotThrow(() -> configService.update(ValidConfigWithPrimitives.class));
        assertThrows(IllegalStateException.class, () -> configService.update(ValidUnRegisteredConfig.class));
    }

    @Test
    void testContent_update() throws IOException {
        FileConfigService<TestConfig> configService = setUpNewConfigService();
        Gson gson = configService.getGson();
        configService.loadAndRegister(new ValidConfigWithObjects());
        configService.loadAndRegister(new ValidConfigWithPrimitives());
        configService.update(ValidConfigWithObjects.class);
        configService.update(ValidConfigWithPrimitives.class);
        Path pathToConfigWithObjects = configService.getConfigFolderPath().resolve("ValidConfigWithObjects.json");
        Path pathToConfigWithPrimitives = configService.getConfigFolderPath().resolve("ValidConfigWithPrimitives.json");

        assertEquals(gson.toJson(new ValidConfigWithObjects()), Files.readString(pathToConfigWithObjects));
        assertEquals(gson.toJson(new ValidConfigWithPrimitives()), Files.readString(pathToConfigWithPrimitives));

        configService.get(ValidConfigWithObjects.class).nonFinalStringObject = "some_Weird_VALUE";
        configService.get(ValidConfigWithPrimitives.class).nonFinalChar = '+';
        configService.get(ValidConfigWithPrimitives.class).nonFinalInt = -518282;
        configService.update(ValidConfigWithObjects.class); // Since we didn't save the current config, the changes above this comment are useless.
        configService.update(ValidConfigWithPrimitives.class);

        assertEquals(gson.toJson(new ValidConfigWithObjects()), Files.readString(pathToConfigWithObjects));
        assertEquals(gson.toJson(new ValidConfigWithPrimitives()), Files.readString(pathToConfigWithPrimitives));
    }

    @Test
    void testExceptions_get() throws IOException {
        ConfigService<TestConfig> configService = setUpNewConfigService();
        configService.loadAndRegister(new ValidConfigWithObjects());
        configService.loadAndRegister(new ValidConfigWithPrimitives());

        assertThrows(NoSuchElementException.class, () -> configService.get(ValidUnRegisteredConfig.class));
        assertDoesNotThrow(() -> configService.get(ValidConfigWithObjects.class));
        assertDoesNotThrow(() -> configService.get(ValidConfigWithPrimitives.class));
    }

    @Test
    void testMethodIsReference_get() throws IOException {
        ConfigService<TestConfig> configService = setUpNewConfigService();
        configService.loadAndRegister(new ValidConfigWithObjects());
        configService.loadAndRegister(new ValidConfigWithPrimitives());

        assertEquals(new ValidConfigWithObjects(), configService.get(ValidConfigWithObjects.class));
        assertEquals(new ValidConfigWithPrimitives(), configService.get(ValidConfigWithPrimitives.class));

        configService.get(ValidConfigWithObjects.class).nonFinalStringObject = "hi";
        configService.get(ValidConfigWithPrimitives.class).nonFinalInt = 555551;

        assertNotEquals(new ValidConfigWithObjects(), configService.get(ValidConfigWithObjects.class));
        assertNotEquals(new ValidConfigWithPrimitives(), configService.get(ValidConfigWithPrimitives.class));
    }

    @Test
    void testMethodReturnsAllValidRegisteredConfigs_getRegistered() throws IOException {
        ConfigService<TestConfig> configService = setUpNewConfigService();
        configService.loadAndRegister(new ValidConfigWithObjects());
        configService.loadAndRegister(new ValidConfigWithPrimitives());
        ignoreException( () -> configService.loadAndRegister(new NotValidUnAnnotatedConfig()) );

        assertEquals(2, configService.getRegistered().size());
        assertTrue(configService.getRegistered().stream().anyMatch(config -> config instanceof ValidConfigWithObjects));
        assertTrue(configService.getRegistered().stream().anyMatch(config -> config instanceof ValidConfigWithPrimitives));
    }

    @Test
    void testMethodReturnsClone_getRegistered() throws IOException {
        ConfigService<TestConfig> configService = setUpNewConfigService();
        configService.loadAndRegister(new ValidConfigWithObjects());
        ignoreException( () -> configService.getRegistered().add(new ValidConfigWithPrimitives()) );

        assertEquals(1, configService.getRegistered().size());
    }

    @Test
    void testExceptions_save() throws IOException {
        ConfigService<TestConfig> configService = setUpNewConfigService();
        configService.loadAndRegister(new ValidConfigWithObjects());
        configService.loadAndRegister(new ValidConfigWithPrimitives());

        assertDoesNotThrow(() -> configService.save(ValidConfigWithObjects.class));
        assertDoesNotThrow(() -> configService.save(ValidConfigWithPrimitives.class));
        assertThrows(NoSuchElementException.class, () -> configService.save(ValidUnRegisteredConfig.class));
    }

    @Test
    void testFileContent_save() throws IOException {
        FileConfigService<TestConfig> configService = setUpNewConfigService();
        Gson gson = configService.getGson();
        configService.loadAndRegister(new ValidConfigWithObjects());
        configService.loadAndRegister(new ValidConfigWithPrimitives());
        configService.save(ValidConfigWithObjects.class);
        configService.save(ValidConfigWithPrimitives.class);
        Path pathToConfigWithObjects = configService.getConfigFolderPath().resolve("ValidConfigWithObjects.json");
        Path pathToConfigWithPrimitives = configService.getConfigFolderPath().resolve("ValidConfigWithPrimitives.json");

        assertEquals(gson.toJson(new ValidConfigWithObjects()), Files.readString(pathToConfigWithObjects));
        assertEquals(gson.toJson(new ValidConfigWithPrimitives()), Files.readString(pathToConfigWithPrimitives));

        configService.get(ValidConfigWithObjects.class).nonFinalStringObject = "some_Weird_VALUE";
        configService.get(ValidConfigWithPrimitives.class).nonFinalChar = '+';
        configService.get(ValidConfigWithPrimitives.class).nonFinalInt = -518282;
        configService.save(ValidConfigWithObjects.class);
        configService.save(ValidConfigWithPrimitives.class);

        assertNotEquals(gson.toJson(new ValidConfigWithObjects()), Files.readString(pathToConfigWithObjects));
        assertNotEquals(gson.toJson(new ValidConfigWithPrimitives()), Files.readString(pathToConfigWithPrimitives));
    }

    @Test
    void testExceptionsAndProperlySaving_isSaved() throws IOException {
        FileConfigService<TestConfig> configService = setUpNewConfigService();
        configService.loadAndRegister(new ValidConfigWithObjects());
        configService.loadAndRegister(new ValidConfigWithPrimitives());

        assertTrue(configService.isSaved(ValidConfigWithObjects.class));
        assertTrue(configService.isSaved(ValidConfigWithPrimitives.class));
        assertFalse(configService.isSaved(ValidUnRegisteredConfig.class));
        assertDoesNotThrow(() -> configService.isSaved(NotValidUnAnnotatedConfig.class));

        Files.createFile(configService.getConfigFolderPath().resolve("ValidUnRegisteredConfig.json"));

        assertTrue(configService.isSaved(ValidUnRegisteredConfig.class));
    }

    @Test
    void testExceptionsAndRegisteredChecking_isRegistered() throws IOException {
        FileConfigService<TestConfig> configService = setUpNewConfigService();
        configService.loadAndRegister(new ValidConfigWithObjects());
        configService.loadAndRegister(new ValidConfigWithPrimitives());
        Files.createFile(configService.getConfigFolderPath().resolve("ValidUnRegisteredConfig.json"));

        assertTrue(configService.isRegistered(ValidConfigWithObjects.class));
        assertTrue(configService.isRegistered(ValidConfigWithPrimitives.class));
        assertFalse(configService.isRegistered(ValidUnRegisteredConfig.class));
        assertDoesNotThrow(() -> configService.isRegistered(NotValidUnAnnotatedConfig.class));
    }

    private static FileConfigService<TestConfig> setUpNewConfigService() throws IOException {
        return new FileConfigService<>(
                Files.createTempDirectory(UUID.randomUUID().toString())
        );
    }

    private static void ignoreException(Executable executable) {
        try {
            executable.execute();
        } catch (Throwable ignored) {
        }
    }

    interface TestConfig {
    }

    @Config(name = "ValidConfigWithObjects")
    static class ValidConfigWithObjects implements TestConfig {
        final String stringObjectFinal = "Hello World";
        String nonFinalStringObject;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof ValidConfigWithObjects that)) return false;
            return Objects.equals(stringObjectFinal, that.stringObjectFinal) && Objects.equals(nonFinalStringObject, that.nonFinalStringObject);
        }
    }

    @Config(name = "ValidConfigWithPrimitives")
    static class ValidConfigWithPrimitives implements TestConfig {
        final int finalInt = 5;
        final char finalChar = '\n';
        int nonFinalInt;
        char nonFinalChar;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof ValidConfigWithPrimitives that)) return false;
            return finalInt == that.finalInt && finalChar == that.finalChar && nonFinalInt == that.nonFinalInt && nonFinalChar == that.nonFinalChar;
        }
    }

    @Config(name = "ValidUnRegisteredConfig")
    static class ValidUnRegisteredConfig implements TestConfig {
        final String someRandomString = "hello";

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof ValidUnRegisteredConfig that)) return false;
            return Objects.equals(someRandomString, that.someRandomString);
        }
    }

    static class NotValidUnAnnotatedConfig implements TestConfig {
        final String someRandomString = "hi";

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof NotValidUnAnnotatedConfig that)) return false;
            return Objects.equals(someRandomString, that.someRandomString);
        }
    }

}