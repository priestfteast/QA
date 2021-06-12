package com.balakin.qa.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.parameters.P;

import java.io.IOException;
import java.io.StringReader;
import java.util.Properties;

@Data
@NoArgsConstructor
public class Row {

    public Row(String row) {
        System.out.println(row);
        String[] blocks = row.split("%");
        this.id = blocks[0].substring(blocks[0].indexOf("_")+1,blocks[0].indexOf("=")).trim();
        this.criteriaName = blocks[0].substring(0,blocks[0].indexOf("=")).trim();
        this.criteria = blocks[0].substring(blocks[0].indexOf("=")+1).trim();
        this.weightName = blocks[1].substring(0,blocks[1].indexOf("=")).trim();
        System.out.println(blocks[1]);
        this.weight = Integer.parseInt(blocks[1].substring(blocks[1].indexOf("=")+1,blocks[1].lastIndexOf(" ")).trim());
        if(blocks[1].substring(blocks[1].lastIndexOf("=")+1).equals("null"))
            this.maxWeight = weight;
        else
            this.maxWeight = Integer.parseInt(blocks[1].substring(blocks[1].lastIndexOf("=")+1).trim());
        System.out.println(this);
    }

    private String id;
    private String criteriaName;
    private String criteria;
    private String weightName;
    private int weight;
    private int maxWeight;

    public static void main(String[] args) throws IOException {
        Properties properties = new Properties();
        properties.load(new StringReader("project=AUCHAN\n" +
                " operator=Чувилькина Екатерина Владиславовна\n" +
                " auditor=Kirill\n" +
                " block_0=Соблюдение сценария разговора\n" +
                " criteria_a0=Соблюдение процедуры приветствия\n" +
                " weight_a0=3\n" +
                " criteria_a11=Соблюдение процедуры персонализации (обращение по имени)\n" +
                " weight_a11=2\n" +
                " criteria_a112=Соблюдение процедуры проверки слышимости\n" +
                " weight_a112=2\n" +
                " criteria_a1123=Соблюдение процедуры снятия с/постановки на удержание и продления удержания\n" +
                " weight_a1123=2\n" +
                " criteria_a11234=Соблюдение процедуры перевода звонка\n" +
                " weight_a11234=3\n" +
                " criteria_a112345=Соблюдение процедуры резюмирования\n" +
                " weight_a112345=3\n" +
                " criteria_a1123456=Соблюдение правил завершения диалога+ финальный скрипт (в том числе Соблюдение процедуры предложения подписок)\n" +
                " weight_a1123456=3\n" +
                " criteria_a11234567=Незатянутое удержание\n" +
                " weight_a11234567=2\n" +
                " block_1=Стиль ведения диалога\n" +
                " criteria_b11=Интонирование (тон голоса не вялый, не раздраженный)\n" +
                " weight_b11=2\n" +
                " criteria_b112=Проявление вежливости и эмпатии\n" +
                " weight_b112=3\n" +
                " criteria_b1123=Грамотная речь\n" +
                " weight_b1123=3\n" +
                " criteria_b11234=Соответствие речи деловому стилю общения\n" +
                " weight_b11234=2\n" +
                " criteria_b112345=Четкая дикция\n" +
                " weight_b112345=2\n" +
                " criteria_b1123456=Отсутствие посторонних звуков (смех, кашель, зевание, жевание и др.)\n" +
                " weight_b1123456=2\n" +
                " criteria_b11234567=Умеренные темп и громкость речи\n" +
                " weight_b11234567=2\n" +
                " criteria_b112345678=Отсутствие использования слов-паразитов\n" +
                " weight_b112345678=2\n" +
                " criteria_b1123456789=Отсутствие перебивания\n" +
                " weight_b1123456789=2\n" +
                " block_2=Решение вопроса\n" +
                " criteria_c11=Использование техники активного слушания\n" +
                " weight_c11=5\n" +
                " criteria_c112=Оператор берет инициативу в разговоре когда это необходимо\n" +
                " weight_c112=5\n" +
                " criteria_c1123=Отсутствие невнимательности (не переспрашивает ранее озвученную информацию)\n" +
                " weight_c1123=5\n" +
                " criteria_c11234=Управление конфликтной ситуацией/отработка возражений\n" +
                " weight_c11234=5\n" +
                " block_3=Работа в CRM (программе фиксации результатов Консультаций)\n" +
                " criteria_d15=Ответ на вопрос предоставлен доходчиво, лаконично и в полном объеме, Если требуется решение, оператор решает вопрос (в рамках своих полномочий) или предлагает альтернативное решение\n" +
                " weight_d15=8\n" +
                " criteria_d156=Проверены детали для получения соответствующей информации\n" +
                " weight_d156=4\n" +
                " criteria_d1567=Проактивность при предоставлении информации\n" +
                " weight_d1567=4\n" +
                " criteria_d15678=Проблема/вопрос оператором поняты/установлены, НЕ игнорирует вопроса клиента\n" +
                " weight_d15678=4\n" +
                " block_4=Работа в CRM (программе фиксации результатов Консультаций)\n" +
                " criteria_e11=Верно указаны тип, тематика, сит и происхождение обращения\n" +
                " weight_e11=6\n" +
                " criteria_e112=При отсутствии контакта в базе он зарегистрирован корректно из CTI-панели (происхождение контакта: КЦ)\n" +
                " weight_e112=3\n" +
                " criteria_e1123=Верно указан статус закрытия обращения\n" +
                " weight_e1123=3\n" +
                " criteria_e11234=информация и дополнения занесены в неискаженном виде, без потери смысла\n" +
                " weight_e11234=6\n" +
                " criteria_e112345=Информация в поля внесена без грамматических ошибок и/или опечаток\n" +
                " weight_e112345=0"));

        System.out.println(properties.get("weight_e112"));
    }

}
