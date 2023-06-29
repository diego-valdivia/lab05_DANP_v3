package com.amplifyframework.datastore.generated.model;

import com.amplifyframework.core.model.temporal.Temporal;

import java.util.List;
import java.util.UUID;
import java.util.Objects;

import androidx.core.util.ObjectsCompat;

import com.amplifyframework.core.model.AuthStrategy;
import com.amplifyframework.core.model.Model;
import com.amplifyframework.core.model.ModelOperation;
import com.amplifyframework.core.model.annotations.AuthRule;
import com.amplifyframework.core.model.annotations.Index;
import com.amplifyframework.core.model.annotations.ModelConfig;
import com.amplifyframework.core.model.annotations.ModelField;
import com.amplifyframework.core.model.query.predicate.QueryField;

import static com.amplifyframework.core.model.query.predicate.QueryField.field;

/** This is an auto generated class representing the LectorFoco type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "LectorFocos", type = Model.Type.USER, version = 1, authRules = {
  @AuthRule(allow = AuthStrategy.PUBLIC, operations = { ModelOperation.CREATE, ModelOperation.UPDATE, ModelOperation.DELETE, ModelOperation.READ })
})
public final class LectorFoco implements Model {
  public static final QueryField ID = field("LectorFoco", "id");
  public static final QueryField INTENSIDAD = field("LectorFoco", "intensidad");
  public static final QueryField HORA = field("LectorFoco", "hora");
  public static final QueryField FECHA = field("LectorFoco", "fecha");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="Int", isRequired = true) Integer intensidad;
  private final @ModelField(targetType="AWSTime", isRequired = true) Temporal.Time hora;
  private final @ModelField(targetType="AWSDate", isRequired = true) Temporal.Date fecha;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  public String resolveIdentifier() {
    return id;
  }
  
  public String getId() {
      return id;
  }
  
  public Integer getIntensidad() {
      return intensidad;
  }
  
  public Temporal.Time getHora() {
      return hora;
  }
  
  public Temporal.Date getFecha() {
      return fecha;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private LectorFoco(String id, Integer intensidad, Temporal.Time hora, Temporal.Date fecha) {
    this.id = id;
    this.intensidad = intensidad;
    this.hora = hora;
    this.fecha = fecha;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      LectorFoco lectorFoco = (LectorFoco) obj;
      return ObjectsCompat.equals(getId(), lectorFoco.getId()) &&
              ObjectsCompat.equals(getIntensidad(), lectorFoco.getIntensidad()) &&
              ObjectsCompat.equals(getHora(), lectorFoco.getHora()) &&
              ObjectsCompat.equals(getFecha(), lectorFoco.getFecha()) &&
              ObjectsCompat.equals(getCreatedAt(), lectorFoco.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), lectorFoco.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getIntensidad())
      .append(getHora())
      .append(getFecha())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("LectorFoco {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("intensidad=" + String.valueOf(getIntensidad()) + ", ")
      .append("hora=" + String.valueOf(getHora()) + ", ")
      .append("fecha=" + String.valueOf(getFecha()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()))
      .append("}")
      .toString();
  }
  
  public static IntensidadStep builder() {
      return new Builder();
  }
  
  /**
   * WARNING: This method should not be used to build an instance of this object for a CREATE mutation.
   * This is a convenience method to return an instance of the object with only its ID populated
   * to be used in the context of a parameter in a delete mutation or referencing a foreign key
   * in a relationship.
   * @param id the id of the existing item this instance will represent
   * @return an instance of this model with only ID populated
   */
  public static LectorFoco justId(String id) {
    return new LectorFoco(
      id,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      intensidad,
      hora,
      fecha);
  }
  public interface IntensidadStep {
    HoraStep intensidad(Integer intensidad);
  }
  

  public interface HoraStep {
    FechaStep hora(Temporal.Time hora);
  }
  

  public interface FechaStep {
    BuildStep fecha(Temporal.Date fecha);
  }
  

  public interface BuildStep {
    LectorFoco build();
    BuildStep id(String id);
  }
  

  public static class Builder implements IntensidadStep, HoraStep, FechaStep, BuildStep {
    private String id;
    private Integer intensidad;
    private Temporal.Time hora;
    private Temporal.Date fecha;
    @Override
     public LectorFoco build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new LectorFoco(
          id,
          intensidad,
          hora,
          fecha);
    }
    
    @Override
     public HoraStep intensidad(Integer intensidad) {
        Objects.requireNonNull(intensidad);
        this.intensidad = intensidad;
        return this;
    }
    
    @Override
     public FechaStep hora(Temporal.Time hora) {
        Objects.requireNonNull(hora);
        this.hora = hora;
        return this;
    }
    
    @Override
     public BuildStep fecha(Temporal.Date fecha) {
        Objects.requireNonNull(fecha);
        this.fecha = fecha;
        return this;
    }
    
    /**
     * @param id id
     * @return Current Builder instance, for fluent method chaining
     */
    public BuildStep id(String id) {
        this.id = id;
        return this;
    }
  }
  

  public final class CopyOfBuilder extends Builder {
    private CopyOfBuilder(String id, Integer intensidad, Temporal.Time hora, Temporal.Date fecha) {
      super.id(id);
      super.intensidad(intensidad)
        .hora(hora)
        .fecha(fecha);
    }
    
    @Override
     public CopyOfBuilder intensidad(Integer intensidad) {
      return (CopyOfBuilder) super.intensidad(intensidad);
    }
    
    @Override
     public CopyOfBuilder hora(Temporal.Time hora) {
      return (CopyOfBuilder) super.hora(hora);
    }
    
    @Override
     public CopyOfBuilder fecha(Temporal.Date fecha) {
      return (CopyOfBuilder) super.fecha(fecha);
    }
  }
  
}
