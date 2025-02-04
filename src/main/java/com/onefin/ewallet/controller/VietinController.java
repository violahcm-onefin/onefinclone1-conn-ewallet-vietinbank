package com.onefin.ewallet.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.onefin.ewallet.common.OneFinConstants;
import com.onefin.ewallet.model.PaymentByOTP;
import com.onefin.ewallet.model.PaymentByOTPResponse;
import com.onefin.ewallet.model.PaymentByToken;
import com.onefin.ewallet.model.PaymentByTokenResponse;
import com.onefin.ewallet.model.ProviderInquiry;
import com.onefin.ewallet.model.ProviderInquiryResponse;
import com.onefin.ewallet.model.RegisterOnlinePay;
import com.onefin.ewallet.model.RegisterOnlinePayResponse;
import com.onefin.ewallet.model.TokenIssue;
import com.onefin.ewallet.model.TokenIssuePayment;
import com.onefin.ewallet.model.TokenIssuePaymentResponse;
import com.onefin.ewallet.model.TokenIssueResponse;
import com.onefin.ewallet.model.TokenReIssueResponse;
import com.onefin.ewallet.model.TokenRevokeReIssue;
import com.onefin.ewallet.model.TokenRevokeResponse;
import com.onefin.ewallet.model.TransactionInquiry;
import com.onefin.ewallet.model.TransactionInquiryResponse;
import com.onefin.ewallet.model.VerifyPin;
import com.onefin.ewallet.model.VerifyPinResponse;
import com.onefin.ewallet.model.Withdraw;
import com.onefin.ewallet.model.WithdrawResponse;
import com.onefin.ewallet.service.IHTTPRequestUtil;
import com.onefin.ewallet.service.IMessageUtil;
import com.onefin.ewallet.service.IVietinService;

@Controller
@Configuration
@RequestMapping("/vietin/ewallet")
public class VietinController {

	@Autowired
	public IVietinService iVietinService;

	@Autowired
	private IMessageUtil imsgUtil;

	@Autowired
	private IHTTPRequestUtil IHTTPRequestUtil;

	private static final Logger LOGGER = LoggerFactory.getLogger(VietinController.class);

	@RequestMapping(method = RequestMethod.POST, value = "/tokenIssue")
	public @ResponseBody ResponseEntity<?> getTokenIssue(@Valid @RequestBody(required = true) TokenIssue requestBody,
			HttpServletRequest request) throws Exception {

		LOGGER.info("== RequestID {} - Send TokenIssue Request to VIETIN", requestBody.getRequestId());
		try {
			TokenIssue requestMap = iVietinService.buildVietinTokenIssuer(requestBody);
			LOGGER.info("== RequestID {} - TokenIssue Request : " + requestMap, requestBody.getRequestId());

			TokenIssueResponse response = (TokenIssueResponse) IHTTPRequestUtil.sendTokenIssue(requestMap);

			// Validate response from VTB
			ResponseEntity<?> responseEntity = iVietinService.validateResponse(response);
			return responseEntity;
		} catch (Exception e) {
			LOGGER.error("== RequestID {} - Fail to process TokenIssue function: {}", requestBody.getRequestId(), e);
			return new ResponseEntity<>(
					imsgUtil.buildVietinConnectorResponse(OneFinConstants.INTERNAL_SERVER_ERROR, null), HttpStatus.OK);
		}

	}

	@RequestMapping(method = RequestMethod.POST, value = "/verifyPin")
	public @ResponseBody ResponseEntity<?> verifyPin(@Valid @RequestBody(required = true) VerifyPin requestBody,
			HttpServletRequest request) throws Exception {

		LOGGER.info("== RequestID {} - Send VerifyPin Request to VIETIN", requestBody.getRequestId());
		try {
			VerifyPin requestMap = iVietinService.buildVietinVerifyPin(requestBody);
			LOGGER.info("== RequestID {} - VerifyPin Request : " + requestMap, requestBody.getRequestId());

			VerifyPinResponse response = (VerifyPinResponse) IHTTPRequestUtil.sendVerifyPin(requestMap);
			// Validate response from VTB
			ResponseEntity<?> responseEntity = iVietinService.validateResponse(response);
			return responseEntity;
		} catch (Exception e) {
			LOGGER.error("== RequestID {} - Fail to process VerifyPin function: {}", requestBody.getRequestId(), e);
			return new ResponseEntity<>(
					imsgUtil.buildVietinConnectorResponse(OneFinConstants.INTERNAL_SERVER_ERROR, null), HttpStatus.OK);
		}

	}

	@RequestMapping(method = RequestMethod.POST, value = "/registerOnlinePay")
	public @ResponseBody ResponseEntity<?> registerOnlinePay(
			@Valid @RequestBody(required = true) RegisterOnlinePay requestBody, HttpServletRequest request)
			throws Exception {

		LOGGER.info("== RequestID {} - Send RegisterOnlinePay Request to VIETIN", requestBody.getRequestId());
		try {
			RegisterOnlinePay requestMap = iVietinService.buildVietinRegisterOnlinePay(requestBody);
			LOGGER.info("== RequestID {} - RegisterOnlinePay Request : " + requestMap, requestBody.getRequestId());

			RegisterOnlinePayResponse response = (RegisterOnlinePayResponse) IHTTPRequestUtil
					.sendRegisterOnlinePay(requestMap);
			// Validate response from VTB
			ResponseEntity<?> responseEntity = iVietinService.validateResponse(response);
			return responseEntity;
		} catch (Exception e) {
			LOGGER.error("== RequestID {} - Fail to process RegisterOnlinePay function: {}", requestBody.getRequestId(),
					e);
			return new ResponseEntity<>(
					imsgUtil.buildVietinConnectorResponse(OneFinConstants.INTERNAL_SERVER_ERROR, null), HttpStatus.OK);
		}

	}

	@RequestMapping(method = RequestMethod.POST, value = "/tokenRevoke")
	public @ResponseBody ResponseEntity<?> tokenRevoke(
			@Valid @RequestBody(required = true) TokenRevokeReIssue requestBody, HttpServletRequest request)
			throws Exception {

		LOGGER.info("== RequestID {} - Send TokenRevoke Request to VIETIN", requestBody.getRequestId());
		try {
			TokenRevokeReIssue requestMap = iVietinService.buildVietinTokenRevoke(requestBody);
			LOGGER.info("== RequestID {} - TokenRevoke Request : " + requestMap, requestBody.getRequestId());

			TokenRevokeResponse response = (TokenRevokeResponse) IHTTPRequestUtil.sendTokenRevoke(requestMap);
			// Validate response from VTB
			ResponseEntity<?> responseEntity = iVietinService.validateResponse(response);
			return responseEntity;
		} catch (Exception e) {
			LOGGER.error("== RequestID {} - Fail to process TokenRevoke function: {}", requestBody.getRequestId(), e);
			return new ResponseEntity<>(
					imsgUtil.buildVietinConnectorResponse(OneFinConstants.INTERNAL_SERVER_ERROR, null), HttpStatus.OK);
		}

	}

	@RequestMapping(method = RequestMethod.POST, value = "/tokenReIssue")
	public @ResponseBody ResponseEntity<?> tokenReIssue(
			@Valid @RequestBody(required = true) TokenRevokeReIssue requestBody, HttpServletRequest request)
			throws Exception {

		LOGGER.info("== RequestID {} - Send TokenReissue Request to VIETIN", requestBody.getRequestId());
		try {
			TokenRevokeReIssue requestMap = iVietinService.buildVietinTokenReIssue(requestBody);
			LOGGER.info("== RequestID {} - TokenReissue Request : " + requestMap, requestBody.getRequestId());

			TokenReIssueResponse response = (TokenReIssueResponse) IHTTPRequestUtil.sendTokenReIssue(requestMap);
			// Validate response from VTB
			ResponseEntity<?> responseEntity = iVietinService.validateResponse(response);
			return responseEntity;
		} catch (Exception e) {
			LOGGER.error("== RequestID {} - Fail to process TokenReissue function: {}", requestBody.getRequestId(), e);
			return new ResponseEntity<>(
					imsgUtil.buildVietinConnectorResponse(OneFinConstants.INTERNAL_SERVER_ERROR, null), HttpStatus.OK);
		}

	}

	@RequestMapping(method = RequestMethod.POST, value = "/paymentByToken")
	public @ResponseBody ResponseEntity<?> paymentByToken(
			@Valid @RequestBody(required = true) PaymentByToken requestBody, HttpServletRequest request)
			throws Exception {

		LOGGER.info("== RequestID {} - Send PaymentByToken Request to VIETIN", requestBody.getRequestId());
		try {
			PaymentByToken requestMap = iVietinService.buildVietinPaymentByToken(requestBody);
			LOGGER.info("== RequestID {} - PaymentByToken Request : " + requestMap, requestBody.getRequestId());

			PaymentByTokenResponse response = (PaymentByTokenResponse) IHTTPRequestUtil.sendPaymentByToken(requestMap);
			// Validate response from VTB
			ResponseEntity<?> responseEntity = iVietinService.validateResponse(response);
			return responseEntity;
		} catch (Exception e) {
			LOGGER.error("== RequestID {} - Fail to process PaymentByToken function: {}", requestBody.getRequestId(),
					e);
			return new ResponseEntity<>(
					imsgUtil.buildVietinConnectorResponse(OneFinConstants.INTERNAL_SERVER_ERROR, null), HttpStatus.OK);
		}

	}

	@RequestMapping(method = RequestMethod.POST, value = "/paymentByOTP")
	public @ResponseBody ResponseEntity<?> paymentByOTP(@Valid @RequestBody(required = true) PaymentByOTP requestBody,
			HttpServletRequest request) throws Exception {

		LOGGER.info("== RequestID {} - Send PaymentByOTP Request to VIETIN", requestBody.getRequestId());
		try {
			PaymentByOTP requestMap = iVietinService.buildVietinPaymentByOTP(requestBody);
			LOGGER.info("== RequestID {} - PaymentByOTP Request : " + requestMap, requestBody.getRequestId());

			PaymentByOTPResponse response = (PaymentByOTPResponse) IHTTPRequestUtil.sendPaymentByOTP(requestMap);
			// Validate response from VTB
			ResponseEntity<?> responseEntity = iVietinService.validateResponse(response);
			return responseEntity;
		} catch (Exception e) {
			LOGGER.error("== RequestID {} - Fail to process PaymentByOTP function: {}", requestBody.getRequestId(), e);
			return new ResponseEntity<>(
					imsgUtil.buildVietinConnectorResponse(OneFinConstants.INTERNAL_SERVER_ERROR, null), HttpStatus.OK);
		}

	}

	@RequestMapping(method = RequestMethod.POST, value = "/withdraw")
	public @ResponseBody ResponseEntity<?> withdraw(@Valid @RequestBody(required = true) Withdraw requestBody,
			HttpServletRequest request) throws Exception {

		LOGGER.info("== RequestID {} - Send Withdraw Request to VIETIN", requestBody.getRequestId());
		try {
			Withdraw requestMap = iVietinService.buildVietinWithdraw(requestBody);
			LOGGER.info("== RequestID {} - Withdraw Request : " + requestMap, requestBody.getRequestId());

			WithdrawResponse response = (WithdrawResponse) IHTTPRequestUtil.sendWithdraw(requestMap);
			// Validate response from VTB
			ResponseEntity<?> responseEntity = iVietinService.validateResponse(response);
			return responseEntity;
		} catch (Exception e) {
			LOGGER.error("== RequestID {} - Fail to process Withdraw function: {}", requestBody.getRequestId(), e);
			return new ResponseEntity<>(
					imsgUtil.buildVietinConnectorResponse(OneFinConstants.INTERNAL_SERVER_ERROR, null), HttpStatus.OK);
		}

	}

	@RequestMapping(method = RequestMethod.POST, value = "/transactionInquiry")
	public @ResponseBody ResponseEntity<?> transactionInquiry(
			@Valid @RequestBody(required = true) TransactionInquiry requestBody, HttpServletRequest request)
			throws Exception {

		LOGGER.info("== RequestID {} - Send TransactionInquiry Request to VIETIN", requestBody.getRequestId());
		try {
			TransactionInquiry requestMap = iVietinService.buildVietinTransactionInquiry(requestBody);
			LOGGER.info("== RequestID {} - TransactionInquiry Request : " + requestMap, requestBody.getRequestId());

			TransactionInquiryResponse response = (TransactionInquiryResponse) IHTTPRequestUtil
					.sendTransactionInquiry(requestMap);
			// Validate response from VTB
			ResponseEntity<?> responseEntity = iVietinService.validateResponse(response);
			return responseEntity;
		} catch (Exception e) {
			LOGGER.error("== RequestID {} - Fail to process TransactionInquiry function: {}",
					requestBody.getRequestId(), e);
			return new ResponseEntity<>(
					imsgUtil.buildVietinConnectorResponse(OneFinConstants.INTERNAL_SERVER_ERROR, null), HttpStatus.OK);
		}

	}

	@RequestMapping(method = RequestMethod.POST, value = "/providerInquiry")
	public @ResponseBody ResponseEntity<?> providerInquiry(
			@Valid @RequestBody(required = true) ProviderInquiry requestBody, HttpServletRequest request)
			throws Exception {

		LOGGER.info("== RequestID {} - Send ProviderInquiry Request to VIETIN", requestBody.getRequestId());
		try {
			ProviderInquiry requestMap = iVietinService.buildVietinProviderInquiry(requestBody);
			LOGGER.info("== RequestID {} - ProviderInquiry Request : " + requestMap, requestBody.getRequestId());

			ProviderInquiryResponse response = (ProviderInquiryResponse) IHTTPRequestUtil
					.sendProviderInquiry(requestMap);
			// Validate response from VTB
			ResponseEntity<?> responseEntity = iVietinService.validateResponse(response);
			return responseEntity;
		} catch (Exception e) {
			LOGGER.error("== RequestID {} - Fail to process ProviderInquiry function: {}", requestBody.getRequestId(),
					e);
			return new ResponseEntity<>(
					imsgUtil.buildVietinConnectorResponse(OneFinConstants.INTERNAL_SERVER_ERROR, null), HttpStatus.OK);
		}

	}

	@RequestMapping(method = RequestMethod.POST, value = "/tokenIssuer-payment")
	public @ResponseBody ResponseEntity<?> getTokenIssuerPayment(
			@Valid @RequestBody(required = true) TokenIssuePayment requestBody, HttpServletRequest request)
			throws Exception {

		LOGGER.info("== RequestID {} - Send TokenIssuePayment Request to VIETIN", requestBody.getRequestId());
		try {
			TokenIssuePayment requestMap = iVietinService.buildVietinTokenIssuerPayment(requestBody);
			LOGGER.info("== RequestID {} - TokenIssuePayment Request : " + requestMap, requestBody.getRequestId());

			TokenIssuePaymentResponse response = (TokenIssuePaymentResponse) IHTTPRequestUtil
					.sendTokenIssuePayment(requestMap);
			// Validate response from VTB
			ResponseEntity<?> responseEntity = iVietinService.validateResponse(response);
			return responseEntity;
		} catch (Exception e) {
			LOGGER.error("== RequestID {} - Fail to process TokenIssuePayment function: {}", requestBody.getRequestId(),
					e);
			return new ResponseEntity<>(
					imsgUtil.buildVietinConnectorResponse(OneFinConstants.INTERNAL_SERVER_ERROR, null), HttpStatus.OK);
		}

	}

}
