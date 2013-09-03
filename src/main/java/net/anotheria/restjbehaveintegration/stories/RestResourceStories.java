package net.anotheria.restjbehaveintegration.stories;

import java.text.SimpleDateFormat;

import java.util.List;

import org.jbehave.core.Embeddable;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.i18n.LocalizedKeywords;
import org.jbehave.core.io.CodeLocations;
import org.jbehave.core.io.LoadFromClasspath;
import org.jbehave.core.io.StoryFinder;
import org.jbehave.core.junit.JUnitStories;
import org.jbehave.core.model.ExamplesTableFactory;
import org.jbehave.core.parsers.RegexStoryParser;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.InstanceStepsFactory;
import org.jbehave.core.steps.ParameterConverters;
import org.jbehave.core.steps.ParameterConverters.DateConverter;
import org.jbehave.core.steps.ParameterConverters.ExamplesTableConverter;

import static org.jbehave.core.reporters.Format.CONSOLE;
import static org.jbehave.core.reporters.Format.HTML;
import static org.jbehave.core.reporters.Format.TXT;
import static org.jbehave.core.reporters.Format.XML;

/**
 * @author daagafonov@gmail.com
 */
public class RestResourceStories extends JUnitStories {

//	@Override
//	public Configuration configuration() {
//		return new MostUsefulConfiguration().useStoryLoader(new LoadFromClasspath(this.getClass())).useStoryReporterBuilder(
//				new StoryReporterBuilder().withCodeLocation(CodeLocations.codeLocationFromClass(this.getClass())).withFormats(Format.CONSOLE,
//						Format.TXT, Format.HTML));
//	}
//
//	@Override
//	public List<CandidateSteps> candidateSteps() {
//		return new InstanceStepsFactory(configuration(), new RestResourceSteps(), new BenefitsResourceSteps()).createCandidateSteps();
//	}
//
//	@Override
//	protected List<String> storyPaths() {
//		return new StoryFinder().findPaths(CodeLocations.codeLocationFromClass(this.getClass()), "**/*.story", "");
//	}
	
	public RestResourceStories() {
        configuredEmbedder().embedderControls().doBatch(true).doGenerateViewAfterStories(true).doIgnoreFailureInStories(true)
        .doIgnoreFailureInView(true).useThreads(1).useStoryTimeoutInSecs(60);
    }

    @Override
    public Configuration configuration() {
        Class<? extends Embeddable> embeddableClass = this.getClass();
        // Start from default ParameterConverters instance
        ParameterConverters parameterConverters = new ParameterConverters();
        // factory to allow parameter conversion and loading from external resources (used by StoryParser too)
        ExamplesTableFactory examplesTableFactory = new ExamplesTableFactory(new LocalizedKeywords(), new LoadFromClasspath(embeddableClass), parameterConverters);
        // add custom converters
        parameterConverters.addConverters(new DateConverter(new SimpleDateFormat("yyyy-MM-dd")),
                new ExamplesTableConverter(examplesTableFactory));
        return new MostUsefulConfiguration()
        .useStoryLoader(new LoadFromClasspath(embeddableClass))
        .useStoryParser(new RegexStoryParser(examplesTableFactory))
        .useStoryReporterBuilder(new StoryReporterBuilder()
        .withCodeLocation(CodeLocations.codeLocationFromClass(embeddableClass))
        .withDefaultFormats()
        .withFormats(CONSOLE, TXT, HTML, XML))
        .useParameterConverters(parameterConverters);
    }

    @Override
    public InjectableStepsFactory stepsFactory() {
        return new InstanceStepsFactory(configuration(), new RestResourceSteps());
    }

    /*
     * find story files from classes dir (when running inside IDE)
     * or from jar file (supported starting with 3.7.5)
     */
    @Override
    protected List<String> storyPaths() {
        return new StoryFinder().findPaths(CodeLocations.codeLocationFromClass(getClass()),
                "**/*.story", "");
    }
	
	public static void main(String[] args) throws Throwable {
		new RestResourceStories().run();
	}

}
