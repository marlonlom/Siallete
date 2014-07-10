package com.s2c.demos.conceptos.web.faces;

import com.s2c.demos.conceptos.data.ConceptoPrecio;
import com.s2c.demos.conceptos.web.faces.util.JsfUtil;
import com.s2c.demos.conceptos.web.faces.util.PaginationHelper;
import com.s2c.demos.conceptos.ejb.ConceptoPrecioFacade;

import java.io.Serializable;
import java.util.Date;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;

/**
 * JSF Managed Bean for ConceptoPrecio Web features
 * @author demo
 */
@Named("conceptoPrecioController")
@SessionScoped
public class ConceptoPrecioController implements Serializable {

    private static final int PAGINATION_SIZE = 5;
    private static final String CONCEPTO_REPEATED_MESG = "ConceptoRepeated";
    private static final String CONCEPTO_LEN_GT = "ConceptoLenGT15";
    private static final String DESCRIPCION_LEN_GT = "DescripcionLenGT255";
    private ConceptoPrecio current;
    private DataModel items = null;
    @EJB
    private com.s2c.demos.conceptos.ejb.ConceptoPrecioFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private String selectedItemConcepto;

    public ConceptoPrecioController() {
    }

    public ConceptoPrecio getSelected() {
        if (current == null) {
            current = new ConceptoPrecio();
            selectedItemIndex = -1;
        }
        return current;
    }

    private ConceptoPrecioFacade getFacade() {
        return ejbFacade;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(PAGINATION_SIZE) {

                @Override
                public int getItemsCount() {
                    return getFacade().count();
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}));
                }
            };
        }
        return pagination;
    }

    public String prepareList() {
        recreateModel();
        return "List";
    }

    public String prepareView() {
        current = (ConceptoPrecio) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new ConceptoPrecio();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            if (current.getConcepto().length() > 15) {
                throw new Exception(CONCEPTO_LEN_GT);
            } else if (current.getDescripcion().length() > 255) {
                throw new Exception(DESCRIPCION_LEN_GT);
            } else {
                boolean conceptoNotRepeated = getFacade().alreadyExistsConcepto(current.getConcepto());
                if (conceptoNotRepeated) {
                    current.setCreado(new Date());
                    getFacade().create(current);
                    JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ConceptoPrecioCreated"));
                    return prepareList();
                } else {
                    throw new Exception(CONCEPTO_REPEATED_MESG);
                }
            }
        } catch (Exception e) {
            if (e.getMessage().equalsIgnoreCase(CONCEPTO_REPEATED_MESG)) {
                JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("ConceptoPrecioRepeatedErrorOccured"));
            } else if (e.getMessage().equalsIgnoreCase(DESCRIPCION_LEN_GT)) {
                JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("ConceptoDescripcionLongitudMayor"));
            } else if (e.getMessage().equalsIgnoreCase(CONCEPTO_LEN_GT)) {
                JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("ConceptoLongitudMayor"));
            } else {
                JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
            return null;
        }
    }

    public String prepareEdit() {
        current = (ConceptoPrecio) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        setSelectedItemConcepto(current.getConcepto());
        return "Edit";
    }

    public String update() {
        try {
            if (current.getConcepto().length() > 15) {
                throw new Exception(CONCEPTO_LEN_GT);
            } else if (current.getDescripcion().length() > 255) {
                throw new Exception(DESCRIPCION_LEN_GT);
            } else {
                if (getSelectedItemConcepto().equalsIgnoreCase(current.getConcepto())) {
                    current.setCreado(new Date());
                    getFacade().edit(current);
                    JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ConceptoPrecioUpdated"));
                    setSelectedItemConcepto("");
                    return "View";
                } else {
                    // Finds if 'concepto' field value is unique within database registries
                    boolean conceptoNotRepeated = getFacade().alreadyExistsConcepto(current.getConcepto());
                    if (conceptoNotRepeated) {
                        current.setCreado(new Date());
                        getFacade().edit(current);
                        JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ConceptoPrecioUpdated"));
                        setSelectedItemConcepto("");
                        return "View";
                    } else {
                        throw new Exception(CONCEPTO_REPEATED_MESG);
                    }
                }
            }
        } catch (Exception e) {
            if (e.getMessage().equalsIgnoreCase(CONCEPTO_REPEATED_MESG)) {
                JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("ConceptoPrecioRepeatedErrorOccured"));
            } else if (e.getMessage().equalsIgnoreCase(DESCRIPCION_LEN_GT)) {
                JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("ConceptoDescripcionLongitudMayor"));
            } else if (e.getMessage().equalsIgnoreCase(CONCEPTO_LEN_GT)) {
                JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("ConceptoLongitudMayor"));
            } else {
                JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
            return null;
        }
    }

    public String destroy() {
        current = (ConceptoPrecio) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
        return "List";
    }

    public String destroyAndView() {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "View";
        } else {
            return prepareList();
        }
    }

    private void performDestroy() {
        try {
            getFacade().remove(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ConceptoPrecioDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    private void updateCurrentItem() {
        int count = getFacade().count();
        if (selectedItemIndex >= count) {
            // selected index cannot be bigger than number of items:
            selectedItemIndex = count - 1;
            // go to previous page if last page disappeared:
            if (pagination.getPageFirstItem() >= count) {
                pagination.previousPage();
            }
        }
        if (selectedItemIndex >= 0) {
            current = getFacade().findRange(new int[]{selectedItemIndex, selectedItemIndex + 1}).get(0);
        }
    }

    public DataModel getItems() {
        if (items == null) {
            items = getPagination().createPageDataModel();
        }
        return items;
    }

    private void recreateModel() {
        items = null;
    }

    private void recreatePagination() {
        pagination = null;
    }

    public String next() {
        getPagination().nextPage();
        recreateModel();
        return "List";
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "List";
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    public ConceptoPrecio getConceptoPrecio(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    public String getConceptoClasificacion(java.lang.Integer clasificacion) {
        String result = "No tiene";
        switch (clasificacion) {
            case 21:
                result = "Estructura";
                break;
            case 31:
                result = "Estructura";
                break;
        }
        return result;
    }

    public String getSelectedItemConcepto() {
        return selectedItemConcepto;
    }

    public void setSelectedItemConcepto(String originalItemConcepto) {
        this.selectedItemConcepto = originalItemConcepto;
    }

    @FacesConverter(forClass = ConceptoPrecio.class)
    public static class ConceptoPrecioControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ConceptoPrecioController controller = (ConceptoPrecioController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "conceptoPrecioController");
            return controller.getConceptoPrecio(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof ConceptoPrecio) {
                ConceptoPrecio o = (ConceptoPrecio) object;
                return getStringKey(o.getCodigo());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + ConceptoPrecio.class.getName());
            }
        }

    }

}
