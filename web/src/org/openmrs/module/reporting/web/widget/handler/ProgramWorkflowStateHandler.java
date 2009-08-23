/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.reporting.web.widget.handler;

import org.openmrs.Program;
import org.openmrs.ProgramWorkflow;
import org.openmrs.ProgramWorkflowState;
import org.openmrs.annotation.Handler;
import org.openmrs.api.context.Context;
import org.openmrs.module.reporting.web.widget.WidgetConfig;
import org.openmrs.module.reporting.web.widget.html.CodedWidget;
import org.openmrs.module.reporting.web.widget.html.Option;
import org.openmrs.module.reporting.web.widget.html.OptionGroup;

/**
 * FieldGenHandler for Enumerated Types
 */
@Handler(supports={ProgramWorkflowState.class}, order=50)
public class ProgramWorkflowStateHandler extends CodedHandler {
	
	/** 
	 * @see CodedHandler#populateOptions(WidgetConfig, CodedWidget)
	 */
	@Override
	public void populateOptions(WidgetConfig config, CodedWidget widget) {
		for (Program p : Context.getProgramWorkflowService().getAllPrograms()) {
			String pn = p.getName();
			for (ProgramWorkflow w : p.getAllWorkflows()) {
				String wn = (w.getName() == null ? w.getConcept().getDisplayString() : w.getName());
				for (ProgramWorkflowState s : w.getStates()) {
					OptionGroup group = new OptionGroup(pn + "-" + wn, null);
					String sn = (s.getName() == null ? s.getConcept().getDisplayString() : s.getName());
					widget.addOption(new Option(s.getUuid(), sn, null, s, group), config);
				}
			}
		}
	}
	
	/** 
	 * @see WidgetHandler#parse(String, Class<?>)
	 */
	@Override
	public Object parse(String input, Class<?> type) {
		return Context.getProgramWorkflowService().getStateByUuid(input);
	}
}
