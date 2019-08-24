package com.yinuo.mock;

import com.yinuo.mock.base.BytePine;
import com.yinuo.mock.base.SocketSender;
import com.yinuo.mock.base.SocketServiceException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

import java.net.URL;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
public class MockConsumptionMain extends Application implements Initializable {

    public TextField pos_money;
    public TextField device_ip;
    public TableView<ConsumptionRecord> table;
    public TableColumn<ConsumptionRecord, String> tb_time;
    public TableColumn<ConsumptionRecord, String> tb_money;
    public TableColumn<ConsumptionRecord, String> tb_msg;
    public TextField success_times;
    public TextField error_times;
    public TextField pos_id;
    public TextField pos_times;
    public TextField pos_interval;
    public Button btn_start;
    public Button btn_stop;
    private ScheduledExecutorService scheduledExecutorService;
    private ObservableList<ConsumptionRecord> objects;
    private IntegerProperty successProperty = new SimpleIntegerProperty(0);
    private IntegerProperty errorProperty = new SimpleIntegerProperty(0);

    public void start(Stage primaryStage) throws Exception {
        Parent load = FXMLLoader.load(getClass().getResource("/MockConsumptionMain.fxml"));
        primaryStage.setScene(new Scene(load));
        primaryStage.setTitle("消费设备");
        primaryStage.getIcons().add(new Image("/image/app.png"));
        primaryStage.show();

        primaryStage.setOnCloseRequest(event -> Platform.exit());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tb_time.setCellValueFactory(cell -> cell.getValue().recordTimeProperty());
        tb_money.setCellValueFactory(cell -> cell.getValue().recordMoneyProperty());
        tb_msg.setCellValueFactory(cell -> cell.getValue().recordMsgProperty());

        objects = FXCollections.observableArrayList();
        table.setItems(objects);

        successProperty.addListener((observable, oldValue, newValue) -> success_times.setText(newValue.toString()));
        errorProperty.addListener((observable, oldValue, newValue) -> error_times.setText(newValue.toString()));
    }

    public void exit(ActionEvent actionEvent) {
        System.exit(1);
    }

    public void clickManyTimes(ActionEvent actionEvent) {
        pos_times.setDisable(true);
    }

    public void clickAnyMoney(ActionEvent actionEvent) {
        pos_money.setDisable(true);
    }

    public void clean(ActionEvent actionEvent) {
        objects.clear();
        successProperty.set(0);
        errorProperty.set(0);
    }

    public void startPos(ActionEvent actionEvent) {
        stopPos(actionEvent);
        int interval = Integer.parseInt(pos_interval.getText());
        final String ip = device_ip.getText();
        btn_start.disableProperty().set(true);
        btn_stop.disableProperty().set(false);
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleWithFixedDelay(() -> run(ip), 100, interval, TimeUnit.MILLISECONDS);
    }

    public void stopPos(ActionEvent actionEvent) {
        btn_start.disableProperty().set(false);
        btn_stop.disableProperty().set(true);
        Optional.ofNullable(scheduledExecutorService).ifPresent(ExecutorService::shutdown);
    }

    public void run(String ip) {
        ConsumptionRecord consumptionRecord;

        try {
            BytePine bytePine = new BytePine(100).add("46be23f2");
            SocketSender socketSender = new SocketSender(ip, 10002);
            socketSender.connect(2000);
            byte[] send = socketSender.send(bytePine.getBytes(), 10);
            consumptionRecord = new ConsumptionRecord(LocalDateTime.now().toString(), "0.1", send[0] == 0 ? "成功" : "失败");
            successProperty.set(successProperty.get()+1);
            log.info("消费成功");
        } catch (SocketServiceException e) {
            errorProperty.set(errorProperty.get()+1);
            consumptionRecord = new ConsumptionRecord(LocalDateTime.now().toString(), "0.1", "失败");
            log.error("消费失败", e);
        }

        objects.add(consumptionRecord);
    }
}
