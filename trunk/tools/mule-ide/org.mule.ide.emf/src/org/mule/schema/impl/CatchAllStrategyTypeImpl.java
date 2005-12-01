/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.mule.schema.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.BasicFeatureMap;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.InternalEList;

import org.mule.schema.CatchAllStrategyType;
import org.mule.schema.EndpointType;
import org.mule.schema.GlobalEndpointType;
import org.mule.schema.PropertiesType;
import org.mule.schema.SchemaPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Catch All Strategy Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.mule.schema.impl.CatchAllStrategyTypeImpl#getMixed <em>Mixed</em>}</li>
 *   <li>{@link org.mule.schema.impl.CatchAllStrategyTypeImpl#getEndpoint <em>Endpoint</em>}</li>
 *   <li>{@link org.mule.schema.impl.CatchAllStrategyTypeImpl#getGlobalEndpoint <em>Global Endpoint</em>}</li>
 *   <li>{@link org.mule.schema.impl.CatchAllStrategyTypeImpl#getProperties <em>Properties</em>}</li>
 *   <li>{@link org.mule.schema.impl.CatchAllStrategyTypeImpl#getClassName <em>Class Name</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class CatchAllStrategyTypeImpl extends EObjectImpl implements CatchAllStrategyType {
	/**
	 * The cached value of the '{@link #getMixed() <em>Mixed</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMixed()
	 * @generated
	 * @ordered
	 */
	protected FeatureMap mixed = null;

	/**
	 * The default value of the '{@link #getClassName() <em>Class Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getClassName()
	 * @generated
	 * @ordered
	 */
	protected static final String CLASS_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getClassName() <em>Class Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getClassName()
	 * @generated
	 * @ordered
	 */
	protected String className = CLASS_NAME_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CatchAllStrategyTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return SchemaPackage.eINSTANCE.getCatchAllStrategyType();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FeatureMap getMixed() {
		if (mixed == null) {
			mixed = new BasicFeatureMap(this, SchemaPackage.CATCH_ALL_STRATEGY_TYPE__MIXED);
		}
		return mixed;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EndpointType getEndpoint() {
		return (EndpointType)getMixed().get(SchemaPackage.eINSTANCE.getCatchAllStrategyType_Endpoint(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetEndpoint(EndpointType newEndpoint, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(SchemaPackage.eINSTANCE.getCatchAllStrategyType_Endpoint(), newEndpoint, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEndpoint(EndpointType newEndpoint) {
		((FeatureMap.Internal)getMixed()).set(SchemaPackage.eINSTANCE.getCatchAllStrategyType_Endpoint(), newEndpoint);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GlobalEndpointType getGlobalEndpoint() {
		return (GlobalEndpointType)getMixed().get(SchemaPackage.eINSTANCE.getCatchAllStrategyType_GlobalEndpoint(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetGlobalEndpoint(GlobalEndpointType newGlobalEndpoint, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(SchemaPackage.eINSTANCE.getCatchAllStrategyType_GlobalEndpoint(), newGlobalEndpoint, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setGlobalEndpoint(GlobalEndpointType newGlobalEndpoint) {
		((FeatureMap.Internal)getMixed()).set(SchemaPackage.eINSTANCE.getCatchAllStrategyType_GlobalEndpoint(), newGlobalEndpoint);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PropertiesType getProperties() {
		return (PropertiesType)getMixed().get(SchemaPackage.eINSTANCE.getCatchAllStrategyType_Properties(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetProperties(PropertiesType newProperties, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(SchemaPackage.eINSTANCE.getCatchAllStrategyType_Properties(), newProperties, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setProperties(PropertiesType newProperties) {
		((FeatureMap.Internal)getMixed()).set(SchemaPackage.eINSTANCE.getCatchAllStrategyType_Properties(), newProperties);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getClassName() {
		return className;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setClassName(String newClassName) {
		String oldClassName = className;
		className = newClassName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SchemaPackage.CATCH_ALL_STRATEGY_TYPE__CLASS_NAME, oldClassName, className));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, Class baseClass, NotificationChain msgs) {
		if (featureID >= 0) {
			switch (eDerivedStructuralFeatureID(featureID, baseClass)) {
				case SchemaPackage.CATCH_ALL_STRATEGY_TYPE__MIXED:
					return ((InternalEList)getMixed()).basicRemove(otherEnd, msgs);
				case SchemaPackage.CATCH_ALL_STRATEGY_TYPE__ENDPOINT:
					return basicSetEndpoint(null, msgs);
				case SchemaPackage.CATCH_ALL_STRATEGY_TYPE__GLOBAL_ENDPOINT:
					return basicSetGlobalEndpoint(null, msgs);
				case SchemaPackage.CATCH_ALL_STRATEGY_TYPE__PROPERTIES:
					return basicSetProperties(null, msgs);
				default:
					return eDynamicInverseRemove(otherEnd, featureID, baseClass, msgs);
			}
		}
		return eBasicSetContainer(null, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object eGet(EStructuralFeature eFeature, boolean resolve) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case SchemaPackage.CATCH_ALL_STRATEGY_TYPE__MIXED:
				return getMixed();
			case SchemaPackage.CATCH_ALL_STRATEGY_TYPE__ENDPOINT:
				return getEndpoint();
			case SchemaPackage.CATCH_ALL_STRATEGY_TYPE__GLOBAL_ENDPOINT:
				return getGlobalEndpoint();
			case SchemaPackage.CATCH_ALL_STRATEGY_TYPE__PROPERTIES:
				return getProperties();
			case SchemaPackage.CATCH_ALL_STRATEGY_TYPE__CLASS_NAME:
				return getClassName();
		}
		return eDynamicGet(eFeature, resolve);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void eSet(EStructuralFeature eFeature, Object newValue) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case SchemaPackage.CATCH_ALL_STRATEGY_TYPE__MIXED:
				getMixed().clear();
				getMixed().addAll((Collection)newValue);
				return;
			case SchemaPackage.CATCH_ALL_STRATEGY_TYPE__ENDPOINT:
				setEndpoint((EndpointType)newValue);
				return;
			case SchemaPackage.CATCH_ALL_STRATEGY_TYPE__GLOBAL_ENDPOINT:
				setGlobalEndpoint((GlobalEndpointType)newValue);
				return;
			case SchemaPackage.CATCH_ALL_STRATEGY_TYPE__PROPERTIES:
				setProperties((PropertiesType)newValue);
				return;
			case SchemaPackage.CATCH_ALL_STRATEGY_TYPE__CLASS_NAME:
				setClassName((String)newValue);
				return;
		}
		eDynamicSet(eFeature, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void eUnset(EStructuralFeature eFeature) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case SchemaPackage.CATCH_ALL_STRATEGY_TYPE__MIXED:
				getMixed().clear();
				return;
			case SchemaPackage.CATCH_ALL_STRATEGY_TYPE__ENDPOINT:
				setEndpoint((EndpointType)null);
				return;
			case SchemaPackage.CATCH_ALL_STRATEGY_TYPE__GLOBAL_ENDPOINT:
				setGlobalEndpoint((GlobalEndpointType)null);
				return;
			case SchemaPackage.CATCH_ALL_STRATEGY_TYPE__PROPERTIES:
				setProperties((PropertiesType)null);
				return;
			case SchemaPackage.CATCH_ALL_STRATEGY_TYPE__CLASS_NAME:
				setClassName(CLASS_NAME_EDEFAULT);
				return;
		}
		eDynamicUnset(eFeature);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean eIsSet(EStructuralFeature eFeature) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case SchemaPackage.CATCH_ALL_STRATEGY_TYPE__MIXED:
				return mixed != null && !mixed.isEmpty();
			case SchemaPackage.CATCH_ALL_STRATEGY_TYPE__ENDPOINT:
				return getEndpoint() != null;
			case SchemaPackage.CATCH_ALL_STRATEGY_TYPE__GLOBAL_ENDPOINT:
				return getGlobalEndpoint() != null;
			case SchemaPackage.CATCH_ALL_STRATEGY_TYPE__PROPERTIES:
				return getProperties() != null;
			case SchemaPackage.CATCH_ALL_STRATEGY_TYPE__CLASS_NAME:
				return CLASS_NAME_EDEFAULT == null ? className != null : !CLASS_NAME_EDEFAULT.equals(className);
		}
		return eDynamicIsSet(eFeature);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (mixed: ");
		result.append(mixed);
		result.append(", className: ");
		result.append(className);
		result.append(')');
		return result.toString();
	}

} //CatchAllStrategyTypeImpl
