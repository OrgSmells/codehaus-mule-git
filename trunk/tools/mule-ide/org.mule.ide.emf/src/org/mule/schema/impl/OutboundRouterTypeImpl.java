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

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.BasicFeatureMap;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.InternalEList;

import org.mule.schema.CatchAllStrategyType;
import org.mule.schema.OutboundRouterType;
import org.mule.schema.SchemaPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Outbound Router Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.mule.schema.impl.OutboundRouterTypeImpl#getMixed <em>Mixed</em>}</li>
 *   <li>{@link org.mule.schema.impl.OutboundRouterTypeImpl#getCatchAllStrategy <em>Catch All Strategy</em>}</li>
 *   <li>{@link org.mule.schema.impl.OutboundRouterTypeImpl#getRouter <em>Router</em>}</li>
 *   <li>{@link org.mule.schema.impl.OutboundRouterTypeImpl#isMatchAll <em>Match All</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class OutboundRouterTypeImpl extends EObjectImpl implements OutboundRouterType {
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
	 * The default value of the '{@link #isMatchAll() <em>Match All</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isMatchAll()
	 * @generated
	 * @ordered
	 */
	protected static final boolean MATCH_ALL_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isMatchAll() <em>Match All</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isMatchAll()
	 * @generated
	 * @ordered
	 */
	protected boolean matchAll = MATCH_ALL_EDEFAULT;

	/**
	 * This is true if the Match All attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean matchAllESet = false;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected OutboundRouterTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return SchemaPackage.eINSTANCE.getOutboundRouterType();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FeatureMap getMixed() {
		if (mixed == null) {
			mixed = new BasicFeatureMap(this, SchemaPackage.OUTBOUND_ROUTER_TYPE__MIXED);
		}
		return mixed;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CatchAllStrategyType getCatchAllStrategy() {
		return (CatchAllStrategyType)getMixed().get(SchemaPackage.eINSTANCE.getOutboundRouterType_CatchAllStrategy(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCatchAllStrategy(CatchAllStrategyType newCatchAllStrategy, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(SchemaPackage.eINSTANCE.getOutboundRouterType_CatchAllStrategy(), newCatchAllStrategy, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCatchAllStrategy(CatchAllStrategyType newCatchAllStrategy) {
		((FeatureMap.Internal)getMixed()).set(SchemaPackage.eINSTANCE.getOutboundRouterType_CatchAllStrategy(), newCatchAllStrategy);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getRouter() {
		return ((FeatureMap)getMixed()).list(SchemaPackage.eINSTANCE.getOutboundRouterType_Router());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isMatchAll() {
		return matchAll;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMatchAll(boolean newMatchAll) {
		boolean oldMatchAll = matchAll;
		matchAll = newMatchAll;
		boolean oldMatchAllESet = matchAllESet;
		matchAllESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SchemaPackage.OUTBOUND_ROUTER_TYPE__MATCH_ALL, oldMatchAll, matchAll, !oldMatchAllESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetMatchAll() {
		boolean oldMatchAll = matchAll;
		boolean oldMatchAllESet = matchAllESet;
		matchAll = MATCH_ALL_EDEFAULT;
		matchAllESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, SchemaPackage.OUTBOUND_ROUTER_TYPE__MATCH_ALL, oldMatchAll, MATCH_ALL_EDEFAULT, oldMatchAllESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetMatchAll() {
		return matchAllESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, Class baseClass, NotificationChain msgs) {
		if (featureID >= 0) {
			switch (eDerivedStructuralFeatureID(featureID, baseClass)) {
				case SchemaPackage.OUTBOUND_ROUTER_TYPE__MIXED:
					return ((InternalEList)getMixed()).basicRemove(otherEnd, msgs);
				case SchemaPackage.OUTBOUND_ROUTER_TYPE__CATCH_ALL_STRATEGY:
					return basicSetCatchAllStrategy(null, msgs);
				case SchemaPackage.OUTBOUND_ROUTER_TYPE__ROUTER:
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
			case SchemaPackage.OUTBOUND_ROUTER_TYPE__MIXED:
				return getMixed();
			case SchemaPackage.OUTBOUND_ROUTER_TYPE__CATCH_ALL_STRATEGY:
				return getCatchAllStrategy();
			case SchemaPackage.OUTBOUND_ROUTER_TYPE__ROUTER:
				return getRouter();
			case SchemaPackage.OUTBOUND_ROUTER_TYPE__MATCH_ALL:
				return isMatchAll() ? Boolean.TRUE : Boolean.FALSE;
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
			case SchemaPackage.OUTBOUND_ROUTER_TYPE__MIXED:
				getMixed().clear();
				getMixed().addAll((Collection)newValue);
				return;
			case SchemaPackage.OUTBOUND_ROUTER_TYPE__CATCH_ALL_STRATEGY:
				setCatchAllStrategy((CatchAllStrategyType)newValue);
				return;
			case SchemaPackage.OUTBOUND_ROUTER_TYPE__ROUTER:
				getRouter().clear();
				getRouter().addAll((Collection)newValue);
				return;
			case SchemaPackage.OUTBOUND_ROUTER_TYPE__MATCH_ALL:
				setMatchAll(((Boolean)newValue).booleanValue());
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
			case SchemaPackage.OUTBOUND_ROUTER_TYPE__MIXED:
				getMixed().clear();
				return;
			case SchemaPackage.OUTBOUND_ROUTER_TYPE__CATCH_ALL_STRATEGY:
				setCatchAllStrategy((CatchAllStrategyType)null);
				return;
			case SchemaPackage.OUTBOUND_ROUTER_TYPE__ROUTER:
				getRouter().clear();
				return;
			case SchemaPackage.OUTBOUND_ROUTER_TYPE__MATCH_ALL:
				unsetMatchAll();
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
			case SchemaPackage.OUTBOUND_ROUTER_TYPE__MIXED:
				return mixed != null && !mixed.isEmpty();
			case SchemaPackage.OUTBOUND_ROUTER_TYPE__CATCH_ALL_STRATEGY:
				return getCatchAllStrategy() != null;
			case SchemaPackage.OUTBOUND_ROUTER_TYPE__ROUTER:
				return !getRouter().isEmpty();
			case SchemaPackage.OUTBOUND_ROUTER_TYPE__MATCH_ALL:
				return isSetMatchAll();
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
		result.append(", matchAll: ");
		if (matchAllESet) result.append(matchAll); else result.append("<unset>");
		result.append(')');
		return result.toString();
	}

} //OutboundRouterTypeImpl
