---
name: SmartFridge AI
colors:
  surface: '#fbf9f9'
  surface-dim: '#dbdad9'
  surface-bright: '#fbf9f9'
  surface-container-lowest: '#ffffff'
  surface-container-low: '#f5f3f3'
  surface-container: '#efeded'
  surface-container-high: '#e9e8e7'
  surface-container-highest: '#e3e2e2'
  on-surface: '#1b1c1c'
  on-surface-variant: '#40493d'
  inverse-surface: '#303031'
  inverse-on-surface: '#f2f0f0'
  outline: '#707a6c'
  outline-variant: '#bfcaba'
  surface-tint: '#1b6d24'
  primary: '#0d631b'
  on-primary: '#ffffff'
  primary-container: '#2e7d32'
  on-primary-container: '#cbffc2'
  inverse-primary: '#88d982'
  secondary: '#964900'
  on-secondary: '#ffffff'
  secondary-container: '#fc820c'
  on-secondary-container: '#5e2c00'
  tertiary: '#005f63'
  on-tertiary: '#ffffff'
  tertiary-container: '#2b787c'
  on-tertiary-container: '#bffcff'
  error: '#ba1a1a'
  on-error: '#ffffff'
  error-container: '#ffdad6'
  on-error-container: '#93000a'
  primary-fixed: '#a3f69c'
  primary-fixed-dim: '#88d982'
  on-primary-fixed: '#002204'
  on-primary-fixed-variant: '#005312'
  secondary-fixed: '#ffdcc6'
  secondary-fixed-dim: '#ffb786'
  on-secondary-fixed: '#311300'
  on-secondary-fixed-variant: '#723600'
  tertiary-fixed: '#a6eff3'
  tertiary-fixed-dim: '#8ad3d7'
  on-tertiary-fixed: '#002021'
  on-tertiary-fixed-variant: '#004f53'
  background: '#fbf9f9'
  on-background: '#1b1c1c'
  surface-variant: '#e3e2e2'
typography:
  display-lg:
    fontFamily: Inter
    fontSize: 57px
    fontWeight: '400'
    lineHeight: 64px
    letterSpacing: -0.25px
  headline-lg:
    fontFamily: Inter
    fontSize: 32px
    fontWeight: '600'
    lineHeight: 40px
  headline-md:
    fontFamily: Inter
    fontSize: 28px
    fontWeight: '600'
    lineHeight: 36px
  headline-sm:
    fontFamily: Inter
    fontSize: 24px
    fontWeight: '500'
    lineHeight: 32px
  headline-sm-mobile:
    fontFamily: Inter
    fontSize: 20px
    fontWeight: '600'
    lineHeight: 26px
  title-lg:
    fontFamily: Inter
    fontSize: 22px
    fontWeight: '500'
    lineHeight: 28px
  body-lg:
    fontFamily: Inter
    fontSize: 16px
    fontWeight: '400'
    lineHeight: 24px
    letterSpacing: 0.5px
  body-md:
    fontFamily: Inter
    fontSize: 14px
    fontWeight: '400'
    lineHeight: 20px
    letterSpacing: 0.25px
  label-lg:
    fontFamily: Inter
    fontSize: 14px
    fontWeight: '500'
    lineHeight: 20px
    letterSpacing: 0.1px
  label-md:
    fontFamily: Inter
    fontSize: 12px
    fontWeight: '500'
    lineHeight: 16px
    letterSpacing: 0.5px
rounded:
  sm: 0.25rem
  DEFAULT: 0.5rem
  md: 0.75rem
  lg: 1rem
  xl: 1.5rem
  full: 9999px
spacing:
  base: 8px
  xs: 4px
  sm: 8px
  md: 16px
  lg: 24px
  xl: 32px
  gutter: 16px
  margin-mobile: 16px
  margin-tablet: 24px
---

## Brand & Style
The design system is rooted in the **Modern Corporate** aesthetic with a strong emphasis on Material 3 principles. It prioritizes utility and clarity to help users manage their nutrition and food waste effortlessly. The brand personality is friendly yet professional—acting as a helpful kitchen assistant rather than just a utility.

The visual language avoids trendy glassmorphism or aggressive glows, opting instead for a "Clean Tactical" approach: clear boundaries, purposeful whitespace, and a high-quality typographic hierarchy. It evokes a sense of freshness and organization, mimicking a well-kept modern kitchen.

## Colors
The palette is inspired by fresh produce and safety signaling.
- **Primary (Green):** Represents freshness and growth. Used for main actions, active states, and branding.
- **Secondary (Orange):** A warm accent used for expiration warnings and status indicators that require attention but aren't critical failures.
- **Surface Strategy:** Uses Material 3 tonal palettes. Surfaces are slightly tinted with the primary hue to create a cohesive environmental feel.
- **Neutral:** A range of cool grays that ensure high contrast for text and iconography.

## Typography
This design system utilizes **Inter** for its exceptional legibility on small mobile displays and high-density information layouts.
- **Headlines:** Use SemiBold (600) weights to provide clear section anchors. 
- **Body Text:** Standardizes on a 16px base for accessibility. 
- **Labels:** Used for chips, button text, and nutritional facts, relying on Medium (500) weight to maintain prominence at smaller sizes.
- **Mobile Scaling:** Headlines scale down on mobile devices (e.g., `headline-sm-mobile`) to prevent awkward line breaks in narrow containers.

## Layout & Spacing
The layout follows a **Fluid Grid** model optimized for Android handheld devices.
- **Grid:** A 4-column grid for mobile and an 8-column grid for tablets.
- **Rhythm:** An 8dp linear scale governs all spacing. 
- **Margins:** 16dp edge margins are mandatory for mobile views to ensure content does not hug the bezel.
- **Vertical Spacing:** 16dp (md) between cards in a list; 24dp (lg) between distinct logical sections.

## Elevation & Depth
Elevation is expressed through **Tonal Layers** supplemented by subtle ambient shadows, strictly following Material 3 guidelines.
- **Level 0 (Flat):** Background color.
- **Level 1 (Card Default):** A subtle tint change and a 1dp-equivalent soft shadow. Used for list items.
- **Level 2 (Floating):** Used for components like the Floating Action Button (FAB), utilizing a more pronounced diffused shadow to indicate interactability.
- **Level 3 (Modal):** Highest elevation for Bottom Sheets and Dialogs, featuring a backdrop dimming effect (scrim) at 30% opacity.
- **Shadows:** Shadows are neutral (black) with very low opacity (8-12%) to keep the UI looking clean and modern.

## Shapes
The shape language is **Rounded**, conveying a friendly and modern tone.
- **Small components (Checkboxes):** 4px radius.
- **Medium components (Cards, Fields):** 12px (rounded-lg) to 16px radius.
- **Large components (Bottom Sheets):** 24px (rounded-xl) top-only radius.
- **Pill shapes:** Used for Chips and Floating Action Buttons to provide high visual contrast against rectangular card content.

## Components
- **Cards:** Use Material 3 Elevated Cards for food items. Include a primary slot for an image, a title-lg for the food name, and a secondary-colored label for expiration dates.
- **Floating Action Button (FAB):** Use a large, pill-shaped FAB in the primary color for the "Add Item" or "Scan Receipt" action.
- **Horizontal Chips:** Used at the top of the "My Fridge" view for filtering (e.g., "Dairy," "Vegetables," "Expiring Soon"). Active chips use a tinted primary background.
- **Bottom Navigation:** Features 3-5 destinations. Use active indicator "pills" around icons to clearly denote the current section.
- **Input Fields:** Outlined style with 12px corner radius. Labels should float to the top border on focus.
- **Lists:** Clean lines with 16px horizontal padding. Use chevron-right icons only if the entire row leads to a new full-screen drill-down.
- **Progress Indicators:** Use linear progress bars within cards to show "Freshness" percentage, transitioning from Primary Green to Secondary Orange as they deplete.