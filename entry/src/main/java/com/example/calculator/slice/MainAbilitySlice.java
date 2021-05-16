package com.example.calculator.slice;

import com.example.calculator.ResourceTable;
import com.example.calculator.eval.SimpleEvaluation;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Button;
import ohos.agp.components.Text;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class MainAbilitySlice extends AbilitySlice {

    private Text expressionText;
    private Text evaluationText;

    private Map<Button, String> operatorButtons;    // 操作符按钮与其对应的在编程语言中的运算符号
    private SimpleEvaluation evaluation;

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);

        bindNumberButtons();
        bindOperatorButtons();
        bindOtherButtons();

        expressionText = (Text) findComponentById(ResourceTable.Id_text_expression);
        evaluationText = (Text) findComponentById(ResourceTable.Id_text_evaluation);

        evaluation = SimpleEvaluation.getInstance();
    }

    private void appendExpression(String s) {
        evaluation.append(s);
        updateExpressionToView();
    }

    private void backspace() {
        evaluation.backspace();
        updateExpressionToView();
    }

    private void updateExpressionToView() {
        expressionText.setText(resolveOperatorInExpression(evaluation.getExpression()));
        evaluationText.setText(evaluation.eval() + "");
    }

    private String resolveOperatorInExpression(String exp) {
        AtomicReference<String> newExp = new AtomicReference<>(exp);
        operatorButtons.forEach((button, s) -> newExp.set(newExp.get().replace(s, button.getText())));
        return newExp.get();
    }

    private void bindOtherButtons() {
        findComponentById(ResourceTable.Id_btn_percent)
                .setClickedListener(btn -> appendExpression("%"));

        findComponentById(ResourceTable.Id_btn_backspace)
                .setClickedListener(btn -> backspace());
    }

    private void bindOperatorButtons() {
        operatorButtons = new HashMap<>();
        operatorButtons.put((Button) findComponentById(ResourceTable.Id_btn_add), "+");
        operatorButtons.put((Button) findComponentById(ResourceTable.Id_btn_sub), "-");
        operatorButtons.put((Button) findComponentById(ResourceTable.Id_btn_multiply), "*");
        operatorButtons.put((Button) findComponentById(ResourceTable.Id_btn_div), "/");
        operatorButtons.put((Button) findComponentById(ResourceTable.Id_btn_point), ".");

        operatorButtons.forEach((button, s) -> button.setClickedListener(btn -> appendExpression(s)));
    }

    private void bindNumberButtons() {
        int[] btnIds = new int[]{
                ResourceTable.Id_btn_num0,
                ResourceTable.Id_btn_num1,
                ResourceTable.Id_btn_num2,
                ResourceTable.Id_btn_num3,
                ResourceTable.Id_btn_num4,
                ResourceTable.Id_btn_num5,
                ResourceTable.Id_btn_num6,
                ResourceTable.Id_btn_num7,
                ResourceTable.Id_btn_num8,
                ResourceTable.Id_btn_num9,
        };
        for (int i = 0; i < btnIds.length; i++) {
            int btnId = btnIds[i];
            final String iString = "" + i;
            findComponentById(btnId).setClickedListener(c -> appendExpression(iString));
        }
    }

    @Override
    public void onActive() {
        super.onActive();
    }

    @Override
    public void onForeground(Intent intent) {
        super.onForeground(intent);
    }
}
