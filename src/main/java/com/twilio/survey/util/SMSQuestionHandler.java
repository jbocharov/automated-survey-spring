package com.twilio.survey.util;

import com.twilio.sdk.verbs.*;
import com.twilio.survey.models.Question;

/**
 * Class responsible of returning the appropriate TwiMLResponse based on the question
 * it receives
 */
public class SMSQuestionHandler implements QuestionHandler{
  Question question;
  private String booleanInstructions =
          "For the next question, type 1 for yes, and 0 for no. " ;
  private String numericInstrunctions =
          "For the next question, please answer with a number. ";
  private String errorMessage =
          "We are sorry, there are no more questions available for this survey. Good bye.";

  public SMSQuestionHandler(Question question) {
    this.question = question;
  }

  /**
   * Bases on the question's type, a specific method is called. This method will construct
   * the specific TwiMLResponse
   */
  public String getTwilioResponse() throws TwiMLException{
    switch (question.getType()) {
      case "numeric":
        return renderTwiMLMessage(numericInstrunctions + question.getBody());
      case "yes-no":
        return renderTwiMLMessage(booleanInstructions + question.getBody());
      default:
        return renderTwiMLMessage(question.getBody());
    }
  }

  public String getHangupResponse() throws TwiMLException { return renderTwiMLMessage(errorMessage); }

  private String renderTwiMLMessage(String content) throws TwiMLException {
    return new TwiMLResponseBuilder().message(content).asString();
  }
}
