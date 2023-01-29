package com.cities.springbatchcities.config;

import com.cities.springbatchcities.dao.City;
import com.cities.springbatchcities.repo.CityRepository;
import lombok.AllArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
@AllArgsConstructor
@EnableBatchProcessing
public class SpringBatchConfig {

    private JobBuilderFactory jobBuilderFactory;

    private StepBuilderFactory stepBuilderFactory;

    private CityRepository cityRepository;

    // Read CSV file
    @Bean
    public FlatFileItemReader<City> reader() {
        return new FlatFileItemReaderBuilder<City>()
                    .name("CityDataReader")
                    .resource(new ClassPathResource("cities.csv"))
                    .delimited()
                    .names(new String[]{"id", "name", "photo"})
                    .fieldSetMapper(new BeanWrapperFieldSetMapper<City>(){{setTargetType(City.class);}})
                    .build();
    }

    // Processing
    @Bean
    public CityProcessor processor() {
        return new CityProcessor();
    }

    // write
    @Bean
    public RepositoryItemWriter<City> writer() {
        RepositoryItemWriter<City> writer = new RepositoryItemWriter<>();
        writer.setRepository(cityRepository);
        writer.setMethodName("save");
        return writer;
    }

    // Step (Read => Process => Write)
    @Bean
    public Step step() {
        return stepBuilderFactory
                .get("cities-csv")
                .<City, City>chunk(10)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }

    @Bean
    public Job runJob() {
        return jobBuilderFactory
                .get("importCity")
                .flow(step())
                .end()
                .build();
    }

}
