package com.continuuity.api;


import com.continuuity.ResourceApp;
import com.continuuity.WordCountApp;
import com.continuuity.api.flow.FlowSpecification;
import com.continuuity.api.flow.FlowletDefinition;
import com.continuuity.internal.app.ApplicationSpecificationAdapter;
import com.continuuity.internal.io.ReflectionSchemaGenerator;
import com.continuuity.internal.io.UnsupportedTypeException;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

/**
 *
 */
public class ApplicationSpecificationTest {

  @Test
  public void testConfigureApplication() throws NoSuchMethodException, UnsupportedTypeException {
    ApplicationSpecification appSpec = new WordCountApp().configure();

    ApplicationSpecificationAdapter adapter = ApplicationSpecificationAdapter.create(new ReflectionSchemaGenerator());

    ApplicationSpecification newSpec = adapter.fromJson(adapter.toJson(appSpec));

    Assert.assertEquals(1, newSpec.getDataSets().size());
    Assert.assertEquals(new ReflectionSchemaGenerator().generate(WordCountApp.MyRecord.class),
                          newSpec.getFlows().get("WordCountFlow").getFlowlets().get("Tokenizer")
                                 .getInputs().get("").iterator().next());
  }

  @Test
  public void testConfigureResourcesApplication() throws UnsupportedTypeException {

    ApplicationSpecification appSpec = new ResourceApp().configure();

    ApplicationSpecificationAdapter adapter = ApplicationSpecificationAdapter.create(new ReflectionSchemaGenerator());

    ApplicationSpecification newSpec = adapter.fromJson(adapter.toJson(appSpec));

    Assert.assertEquals(1, newSpec.getFlows().size());
    Assert.assertTrue(newSpec.getFlows().containsKey("ResourceFlow"));
    FlowSpecification flowSpec = newSpec.getFlows().get("ResourceFlow");

    Assert.assertEquals(2, flowSpec.getFlowlets().size());
    Assert.assertTrue(flowSpec.getFlowlets().containsKey("A"));
    Assert.assertTrue(flowSpec.getFlowlets().containsKey("B"));

    FlowletDefinition flowletA = flowSpec.getFlowlets().get("A");
    Assert.assertEquals(2, flowletA.getResourceSpec().getCores());
    Assert.assertEquals(1024, flowletA.getResourceSpec().getMemorySize());
    FlowletDefinition flowletB = flowSpec.getFlowlets().get("B");
    Assert.assertEquals(5, flowletB.getResourceSpec().getCores());
    Assert.assertEquals(2048, flowletB.getResourceSpec().getMemorySize());
  }
}
