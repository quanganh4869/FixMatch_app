---
name: Pro-Service Marketplace System
colors:
  surface: '#f8f9fa'
  surface-dim: '#d9dadb'
  surface-bright: '#f8f9fa'
  surface-container-lowest: '#ffffff'
  surface-container-low: '#f3f4f5'
  surface-container: '#edeeef'
  surface-container-high: '#e7e8e9'
  surface-container-highest: '#e1e3e4'
  on-surface: '#191c1d'
  on-surface-variant: '#434655'
  inverse-surface: '#2e3132'
  inverse-on-surface: '#f0f1f2'
  outline: '#737687'
  outline-variant: '#c3c5d8'
  surface-tint: '#004ee7'
  primary: '#0043c8'
  on-primary: '#ffffff'
  primary-container: '#1e5af2'
  on-primary-container: '#e5e8ff'
  inverse-primary: '#b6c4ff'
  secondary: '#775a00'
  on-secondary: '#ffffff'
  secondary-container: '#fec72b'
  on-secondary-container: '#6f5400'
  tertiary: '#4d5060'
  on-tertiary: '#ffffff'
  tertiary-container: '#656879'
  on-tertiary-container: '#e6e8fc'
  error: '#ba1a1a'
  on-error: '#ffffff'
  error-container: '#ffdad6'
  on-error-container: '#93000a'
  primary-fixed: '#dce1ff'
  primary-fixed-dim: '#b6c4ff'
  on-primary-fixed: '#001550'
  on-primary-fixed-variant: '#003ab1'
  secondary-fixed: '#ffdf98'
  secondary-fixed-dim: '#f5bf21'
  on-secondary-fixed: '#251a00'
  on-secondary-fixed-variant: '#5a4300'
  tertiary-fixed: '#e0e1f5'
  tertiary-fixed-dim: '#c3c5d9'
  on-tertiary-fixed: '#181b29'
  on-tertiary-fixed-variant: '#434656'
  background: '#f8f9fa'
  on-background: '#191c1d'
  surface-variant: '#e1e3e4'
typography:
  display-lg:
    fontFamily: Inter
    fontSize: 32px
    fontWeight: '700'
    lineHeight: 40px
    letterSpacing: -0.02em
  headline-lg:
    fontFamily: Inter
    fontSize: 24px
    fontWeight: '600'
    lineHeight: 32px
    letterSpacing: -0.01em
  headline-md:
    fontFamily: Inter
    fontSize: 20px
    fontWeight: '600'
    lineHeight: 28px
  body-lg:
    fontFamily: Inter
    fontSize: 16px
    fontWeight: '400'
    lineHeight: 24px
  body-md:
    fontFamily: Inter
    fontSize: 14px
    fontWeight: '400'
    lineHeight: 20px
  label-lg:
    fontFamily: Inter
    fontSize: 14px
    fontWeight: '600'
    lineHeight: 20px
  label-sm:
    fontFamily: Inter
    fontSize: 12px
    fontWeight: '500'
    lineHeight: 16px
    letterSpacing: 0.05em
  headline-lg-mobile:
    fontFamily: Inter
    fontSize: 22px
    fontWeight: '600'
    lineHeight: 28px
rounded:
  sm: 0.25rem
  DEFAULT: 0.5rem
  md: 0.75rem
  lg: 1rem
  xl: 1.5rem
  full: 9999px
spacing:
  base: 4px
  xs: 4px
  sm: 8px
  md: 16px
  lg: 24px
  xl: 32px
  gutter: 16px
  margin-mobile: 16px
  margin-tablet: 32px
---

## Brand & Style

This design system is built for a mobile-first home-service marketplace that balances professional reliability with a friendly, approachable personality. Inspired by the provided mascot, the visual language moves away from cold corporate aesthetics toward a warm, human-centric "Modern & Tactile" style.

The brand personality is **Trustworthy**, **Energetic**, and **Helpful**. It evokes the feeling of a reliable expert coming to the rescue. The UI uses high-quality white space, large tap targets, and smooth rounded corners to ensure the experience feels premium yet accessible for users of all technical abilities.

By leveraging the mascot's vibrant palette against clean off-white backgrounds, the interface maintains high legibility and a systematic, Material 3-aligned structure that feels cohesive across both light and dark modes.

## Colors

The color palette is directly sampled from the mascot to ensure brand consistency. 

- **Primary (Professional Blue):** A vibrant, high-contrast blue used for key actions, brand moments, and primary buttons. It signifies authority and security.
- **Secondary (Warm Yellow):** Used sparingly as an accent for highlights, ratings (stars), and notifications. It adds warmth and friendliness to the interface.
- **Tertiary (Deep Charcoal):** Derived from the mascot's trousers, this color serves as the text and icon base to ensure maximum readability and a premium feel.
- **Neutral (Off-White):** A soft foundation that prevents eye strain. In dark mode, this transitions to a deep navy-charcoal palette to maintain depth.

**Color Usage:**
- Use **Primary** for call-to-action buttons.
- Use **Secondary** for "Success" states, star ratings, and subtle "New" badges.
- Ensure all text-on-color combinations meet WCAG AA contrast standards for accessibility.

## Typography

This design system utilizes **Inter** for its exceptional legibility and systematic character. The hierarchy is designed for quick scanning on mobile devices.

- **Headlines:** Use Bold and Semi-Bold weights to anchor the page. Tighten letter-spacing slightly on larger sizes for a more premium, "locked-in" editorial look.
- **Body:** Stick to the 16px base for main content to ensure accessibility for older demographics who may be hiring home services.
- **Labels:** Use Medium and Semi-Bold weights for button text and category chips to differentiate them from static body copy.

## Layout & Spacing

The system follows a strict **8px grid** (with a 4px half-step for micro-adjustments). This ensures a rhythmic, organized feel across all screen sizes.

**Mobile-First Layout:**
- **Margins:** 16px side margins on all mobile views.
- **Gutter:** 16px between vertical list elements.
- **Safe Areas:** Adhere strictly to mobile notch and home indicator safe areas.
- **Fluid Grid:** Use a 4-column layout for mobile and 8-column for tablet. Components should expand horizontally to fill the screen width minus margins.

Vertical rhythm is prioritized; maintain `md` (16px) or `lg` (24px) spacing between distinct content sections to keep the "clean and premium" look.

## Elevation & Depth

To match the mascot's soft-rendered aesthetic, elevation is achieved through **Tonal Layers** and **Ambient Shadows**.

- **Level 0 (Base):** Off-white background (`#F8F9FA`).
- **Level 1 (Cards):** Pure White (`#FFFFFF`) with a very soft, diffused shadow: `0px 4px 12px rgba(30, 90, 242, 0.05)`. Note the subtle blue tint in the shadow to keep it "professional."
- **Level 2 (Floating Action Buttons/Modals):** Increased elevation with a 20% opacity shadow for higher contrast.
- **Interaction:** When a user taps a card, it should not lift further; instead, use a subtle "pressed" state by darkening the surface slightly or adding a 1px inner border.

Avoid heavy black shadows or flat outlines. The goal is to make surfaces look "pillowy" and inviting.

## Shapes

The shape language is "Rounded-Premium." We avoid sharp corners entirely to maintain the friendly brand voice established by the mascot.

- **Standard Components:** 8px (0.5rem) for buttons and input fields.
- **Large Components:** 16px (1rem) for main service cards and profile images.
- **Containers/Sheets:** 24px (1.5rem) for top-corners of bottom sheets and large container wrappers.

Circular (pill) shapes are reserved for status chips (e.g., "Confirmed," "In Progress") and profile avatars to provide visual variety.

## Components

### Buttons
- **Primary:** Full-width (on mobile), Primary Blue background, White text, 8px corners. Height: 56px for "thumb-friendly" accessibility.
- **Secondary:** White background with a 1.5px Primary Blue border.
- **Tertiary:** Transparent background with Primary Blue text for "Cancel" or "Skip" actions.

### Cards
- White background, 16px rounded corners, subtle shadow.
- Imagery (Service Providers) should have a 12px inner radius within the card.

### Input Fields
- 56px height, Off-white background, 1px border (`#E1E4E8`).
- On focus, the border transitions to 2px Primary Blue with a subtle glow.

### Chips & Badges
- **Service Chips:** Light blue background (`#E8F0FE`) with Primary Blue text.
- **Status Badges:** Use Secondary Yellow for "Pending" and mascot-green for "Completed."

### Navigation
- **Bottom Bar:** Blur-effect background with 4 icon-based destinations. Active state uses a Primary Blue indicator dot or icon tint.

### Jetpack Compose Guidance
- Map `primary` to `#1E5AF2`.
- Map `surface` to `#FFFFFF`.
- Use `Shapes.medium` for 16dp corners.
- Leverage `MaterialTheme.typography` to map the Inter tokens for consistent scaling.