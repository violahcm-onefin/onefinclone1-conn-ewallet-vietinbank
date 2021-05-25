package com.onefin.ewallet.service;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.onefin.ewallet.common.VietinConstants;
import com.onefin.ewallet.common.base.service.ConnMessageService;
import com.onefin.ewallet.common.utility.json.JSONHelper;
import com.onefin.ewallet.model.VietinConnResponse;

@Service
public class MessageUtilImpl extends ConnMessageService implements IMessageUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(MessageUtilImpl.class);

	@Autowired
	private ConfigLoader configLoader;

	@Autowired
	@Qualifier("jsonHelper")
	private JSONHelper JsonHelper;

	@Override
	protected Object buildConnectorInvalidRequestbody(String code, Object data, String... args) {
		return buildVietinConnectorResponse(code, data, args);
	}

	@Override
	public VietinConnResponse buildVietinConnectorResponse(String code, Object data, String... args) {
		Map<String, Object> res = new HashMap();
		if (data != null) {
			try {
				res = (Map<String, Object>) JsonHelper.convertObject2Map(data, Map.class);
				res.remove(VietinConstants.VTB_SIGNATURE);
			} catch (Exception e) {
				LOGGER.error("== Fail to parse Vietin response to Map");
			}
		}
		VietinConnResponse response = new VietinConnResponse();
		response.setConnectorCode(code);
		response.setVtbResponse(res);
		response.setVersion(configLoader.getVietinVersion());
		response.setType(args != null ? args[0] : null);
		return response;
	}

}
