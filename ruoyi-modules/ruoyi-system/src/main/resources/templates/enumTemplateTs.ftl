export enum ${enumTypeName} {
<#list items as item>
    /**
    * ${item.label}
    */
    ${item.eng} = '${item.value}'<#if item_has_next>,</#if>
</#list>
}
