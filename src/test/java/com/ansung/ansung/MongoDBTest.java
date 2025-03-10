package com.ansung.ansung;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.query.Query;

import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@SpringBootTest(properties = "de.flapdoodle.mongodb.embedded.version=5.0.5")
class MongoDBTest {
	@Autowired
	private MongoTemplate template;
	
	@AfterEach
	public void cleanUpDB() {
		template.remove(new Query(),"test");
	}
	
	@Document(collection = "test")
	@Getter
	@Setter
	private class TestEntity{
		@Id
		private String id;
		private String value;
		
	}
	
	@Test
	@DisplayName("save entity test")
	public void test() {
		TestEntity test = new TestEntity();
		test.setValue("save Test Entity");
		template.save(test);
	}
	@Test
	@DisplayName("select entity test")
	public void selectTest() {

		TestEntity test = new TestEntity();
		test.setValue("select Test Entity");
		template.save(test);
		String id = test.getId();
		TestEntity result = template.findById(id, TestEntity.class);
		assertThat(result.getId()).isEqualTo(test.getId());
	}
	@Test
	@DisplayName("collection Test")
	public void collectionTest() {
		int testCount = 10502;
		for(int i =0; i<testCount; i++) {
			TestEntity test = new TestEntity();
			test.setValue("test num : "+i);
			template.save(test);
		}
		List<TestEntity> testEntitys = template.find(new Query(), TestEntity.class, "test");
		assertThat(testEntitys.size()).isEqualTo(testCount);
	}
}
