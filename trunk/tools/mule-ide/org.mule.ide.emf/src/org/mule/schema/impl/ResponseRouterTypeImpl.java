/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.mule.schema.impl;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.BasicFeatureMap;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.InternalEList;

import org.mule.schema.ResponseRouterType;
import org.mule.schema.SchemaPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Response Router Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.mule.schema.impl.ResponseRouterTypeImpl#getMixed <em>Mixed</em>}</li>
 *   <li>{@link org.mule.schema.impl.ResponseRouterTypeImpl#getEndpoint <em>Endpoint</em>}</li>
 *   <li>{@link org.mule.schema.impl.ResponseRouterTypeImpl#getGlobalEndpoint <em>Global Endpoint</em>}</li>
 *   <li>{@link org.mule.schema.impl.ResponseRouterTypeImpl#getRouter <em>Router</em>}</li>
 *   <li>{@link org.mule.schema.impl.ResponseRouterTypeImpl#getTimeout <em>Timeout</em>}</li>
 *   <li>{@link org.mule.schema.impl.ResponseRouterTypeImpl#getTransformers <em>Transformers</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ResponseRouterTypeImpl extends EObjectImpl implements ResponseRouterType {
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
	 * The default value of the '{@link #getTimeout() <em>Timeout</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTimeout()
	 * @generated
	 * @ordered
	 */
	protected static final String TIMEOUT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getTimeout() <em>Timeout</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTimeout()
	 * @generated
	 * @ordered
	 */
	protected String timeout = TIMEOUT_EDEFAULT;

	/**
	 * The default value of the '{@link #getTransformers() <em>Transformers</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTransformers()
	 * @generated
	 * @ordered
	 */
	protected static final List TRANSFORMERS_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getTransformers() <em>Transformers</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTransformers()
	 * @generated
	 * @ordered
	 */
	protected List transformers = TRANSFORMERS_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ResponseRouterTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return SchemaPackage.eINSTANCE.getResponseRouterType();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FeatureMap getMixed() {
		if (mixed == null) {
			mixed = new BasicFeatureMap(this, SchemaPackage.RESPONSE_ROUTER_TYPE__MIXED);
		}
		return mixed;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getEndpoint() {
		return ((FeatureMap)getMixed()).list(SchemaPackage.eINSTANCE.getResponseRouterType_Endpoint());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getGlobalEndpoint() {
		return ((FeatureMap)getMixed()).list(SchemaPackage.eINSTANCE.getResponseRouterType_GlobalEndpoint());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getRouter() {
		return ((FeatureMap)getMixed()).list(SchemaPackage.eINSTANCE.getResponseRouterType_Router());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getTimeout() {
		return timeout;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTimeout(String newTimeout) {
		String oldTimeout = timeout;
		timeout = newTimeout;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SchemaPackage.RESPONSE_ROUTER_TYPE__TIMEOUT, oldTimeout, timeout));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public List getTransformers() {
		return transformers;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTransformers(List newTransformers) {
		List oldTransformers = transformers;
		transformers = newTransformers;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SchemaPackage.RESPONSE_ROUTER_TYPE__TRANSFORMERS, oldTransformers, transformers));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, Class baseClass, NotificationChain msgs) {
		if (featureID >= 0) {
			switch (eDerivedStructuralFeatureID(featureID, baseClass)) {
				case SchemaPackage.RESPONSE_ROUTER_TYPE__MIXED:
					return ((InternalEList)getMixed()).basicRemove(otherEnd, msgs);
				case SchemaPackage.RESPONSE_ROUTER_TYPE__ENDPOINT:
					return ((InternalEList)getEndpoint()).basicRemove(otherEnd, msgs);
				case SchemaPackage.RESPONSE_ROUTER_TYPE__GLOBAL_ENDPOINT:
					return ((InternalEList)getGlobalEndpoint()).basicRemove(otherEnd, msgs);
				case SchemaPackage.RESPONSE_ROUTER_TYPE__ROUTER:
					return ((InternalEList)getRouter()).basicRemove(otherEnd, msgs);
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
			case SchemaPackage.RESPONSE_ROUTER_TYPE__MIXED:
				return getMixed();
			case SchemaPackage.RESPONSE_ROUTER_TYPE__ENDPOINT:
				return getEndpoint();
			case SchemaPackage.RESPONSE_ROUTER_TYPE__GLOBAL_ENDPOINT:
				return getGlobalEndpoint();
			case SchemaPackage.RESPONSE_ROUTER_TYPE__ROUTER:
				return getRouter();
			case SchemaPackage.RESPONSE_ROUTER_TYPE__TIMEOUT:
				return getTimeout();
			case SchemaPackage.RESPONSE_ROUTER_TYPE__TRANSFORMERS:
				return getTransformers();
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
			case SchemaPackage.RESPONSE_ROUTER_TYPE__MIXED:
				getMixed().clear();
				getMixed().addAll((Collection)newValue);
				return;
			case SchemaPackage.RESPONSE_ROUTER_TYPE__ENDPOINT:
				getEndpoint().clear();
				getEndpoint().addAll((Collection)newValue);
				return;
			case SchemaPackage.RESPONSE_ROUTER_TYPE__GLOBAL_ENDPOINT:
				getGlobalEndpoint().clear();
				getGlobalEndpoint().addAll((Collection)newValue);
				return;
			case SchemaPackage.RESPONSE_ROUTER_TYPE__ROUTER:
				getRouter().clear();
				getRouter().addAll((Collection)newValue);
				return;
			case SchemaPackage.RESPONSE_ROUTER_TYPE__TIMEOUT:
				setTimeout((String)newValue);
				return;
			case SchemaPackage.RESPONSE_ROUTER_TYPE__TRANSFORMERS:
				setTransformers((List)newValue);
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
			case SchemaPackage.RESPONSE_ROUTER_TYPE__MIXED:
				getMixed().clear();
				return;
			case SchemaPackage.RESPONSE_ROUTER_TYPE__ENDPOINT:
				getEndpoint().clear();
				return;
			case SchemaPackage.RESPONSE_ROUTER_TYPE__GLOBAL_ENDPOINT:
				getGlobalEndpoint().clear();
				return;
			case SchemaPackage.RESPONSE_ROUTER_TYPE__ROUTER:
				getRouter().clear();
				return;
			case SchemaPackage.RESPONSE_ROUTER_TYPE__TIMEOUT:
				setTimeout(TIMEOUT_EDEFAULT);
				return;
			case SchemaPackage.RESPONSE_ROUTER_TYPE__TRANSFORMERS:
				setTransformers(TRANSFORMERS_EDEFAULT);
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
			case SchemaPackage.RESPONSE_ROUTER_TYPE__MIXED:
				return mixed != null && !mixed.isEmpty();
			case SchemaPackage.RESPONSE_ROUTER_TYPE__ENDPOINT:
				return !getEndpoint().isEmpty();
			case SchemaPackage.RESPONSE_ROUTER_TYPE__GLOBAL_ENDPOINT:
				return !getGlobalEndpoint().isEmpty();
			case SchemaPackage.RESPONSE_ROUTER_TYPE__ROUTER:
				return !getRouter().isEmpty();
			case SchemaPackage.RESPONSE_ROUTER_TYPE__TIMEOUT:
				return TIMEOUT_EDEFAULT == null ? timeout != null : !TIMEOUT_EDEFAULT.equals(timeout);
			case SchemaPackage.RESPONSE_ROUTER_TYPE__TRANSFORMERS:
				return TRANSFORMERS_EDEFAULT == null ? transformers != null : !TRANSFORMERS_EDEFAULT.equals(transformers);
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
		result.append(", timeout: ");
		result.append(timeout);
		result.append(", transformers: ");
		result.append(transformers);
		result.append(')');
		return result.toString();
	}

} //ResponseRouterTypeImpl
