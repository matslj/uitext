package se.mlj.uitext.model.car;

import java.io.IOException;
import java.io.Serializable;
import static org.omnifaces.util.Faces.redirect;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.SelectEvent;
 
@ManagedBean(name="dtBasicView")
@ViewScoped
public class BasicView implements Serializable {
     
    private List<Car> cars;
     
    @ManagedProperty("#{carService}")
    private CarService service;
 
    private Car selectedCar;
    
    @PostConstruct
    public void init() {
        cars = service.createCars(10);
    }
     
    public List<Car> getCars() {
        return cars;
    }
 
    public void setService(CarService service) {
        this.service = service;
    }
    
    public Car getSelectedCar() {
        return selectedCar;
    }
 
    public void setSelectedCar(Car selectedCar) {
        this.selectedCar = selectedCar;
    }
 
    public void onRowSelect(SelectEvent event) throws IOException {
        redirect("/planer/plan");
    }

}
