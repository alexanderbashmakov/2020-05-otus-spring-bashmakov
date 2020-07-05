package ru.otus.spring.domain;

public class TestingResult {
    private int correctAnswers = 0;
    private final int answerCount;

    public TestingResult(int answerCount) {
        this.answerCount = answerCount;
    }

    public void checkAnswer(String answer, String correctAnswer) {
        correctAnswers += correctAnswer.equals("0") || correctAnswer.equals(answer) ? 1 : 0;
    }

    public boolean checkResult(float minCorrectAnswerPercent) {
        return 1.0 * correctAnswers / answerCount >= minCorrectAnswerPercent / 100;
    }

    public int getCorrectAnswers() {
        return correctAnswers;
    }
}
